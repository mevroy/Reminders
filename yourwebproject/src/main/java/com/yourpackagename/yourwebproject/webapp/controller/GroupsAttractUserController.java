/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupInterests;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.RegisterInterest;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupInterestService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.LoggerService;
import com.yourpackagename.yourwebproject.service.RegisterInterestService;


/**
 * @author mevan.d.souza
 *
 */
@Controller
@EnableLogging(loggerClass = "GroupsAttractUserController")
@CheckPermission(allowedRoles = { Role.ANONYMOUS})
public class GroupsAttractUserController extends BaseWebAppController{
	private @Autowired RegisterInterestService registerInterestService;
	private @Autowired GroupInterestService groupInterestService;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupEmailsService groupEmailsService;
	private @Autowired GroupMembersService groupMembersService;
	private @Autowired MailSenderUntypedActor mailSenderUntypedActor;
	private @Autowired LoggerService loggerService;
	protected @Autowired Props props;
	private Logger log = LoggerFactory
			.getLogger(GroupsAttractUserController.class);

	@RequestMapping(value = "/registerInterest", method = {RequestMethod.GET, RequestMethod.HEAD})
	public String registerInterestRequest(Model model,
			@PathVariable String groupCode,
			@RequestParam(required = false) String groupEventCode,
			@RequestParam(required = false) String serialNumber,
			@RequestParam(required = false) String interestType,
			@RequestParam(required = false) String campaign) {

		RegisterInterest registerInterest = new RegisterInterest();
		registerInterest.setCampaign(campaign);
		registerInterest.setInterestType(interestType);
		registerInterest.setGroupCode(groupCode);
		List<GroupInterests> groupInterests = groupInterestService
				.findByGroupCode(groupCode, false);
		boolean interestExists = false;
		for (GroupInterests groupInterest : groupInterests) {
			if (groupInterest.getInterestType().equalsIgnoreCase(interestType)) {
				interestType = groupInterest.getInterestType();
				interestExists = true;
				break;
			}
		}
		if (StringUtils.isNotBlank(interestType) && !interestExists) {

			if (isInActive(groupCode, interestType)) {
				model.addAttribute("popupModal", true);
				model.addAttribute("popupTitle", "Oooops!");
				model.addAttribute(
						"popupMessage",
						"Please note that the registration of interest for \"<b>"
								+ interestType
								+ "</b>\" has expired or not active at the moment. Registration of interests for some events will be time bound. You may register your interest for the ones that are active! We apologize for any inconvenience this might have caused.");

			} else {
				GroupInterests newGe = new GroupInterests();
				newGe.setInterestType(interestType);
				groupInterests.add(newGe);
			}
		}

		model.addAttribute("groupInterests", groupInterests);
		model.addAttribute("registerInterest", registerInterest);
		model.addAttribute("serialNumber", serialNumber);
		return "registerInterest";
	}

	@RequestMapping(value = "/saveInterest", method = RequestMethod.POST)
	public String saveInterest(
			Model model,
			@ModelAttribute("registerInterest") RegisterInterest registerInterest,
			@RequestParam(required = false) String serialNumber,
			HttpServletRequest request) throws Exception {
		registerInterest.setPrimaryEmailVerified(true);
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		registerInterest.setRequestIp(ipAddress);
		String successMessage = "";
		if (registerInterest.getId() != null && registerInterest.getId() > 0) {
			RegisterInterest existingInterest = registerInterestService
					.findById(registerInterest.getId());
			if (registerInterest.getPrimaryEmail().equalsIgnoreCase(
					existingInterest.getPrimaryEmail())
					&& registerInterest.getInterestType().equalsIgnoreCase(
							existingInterest.getInterestType())) {
				registerInterest.setUpdatedAt(Calendar.getInstance().getTime());
				log.info("Same request. Hence just updating the exiting one");
			} else {
				registerInterest.setId(null);

			}
		}

		if (registerInterest.getId() == null) {
			GroupInterests gI = null;

			try {
				gI = groupInterestService.findByInterestType(
						registerInterest.getGroupCode(),
						registerInterest.getInterestType(), false);
			} catch (Exception e) {
			}
			if (gI != null && gI.isSendAutoResponse()
					&& StringUtils.isNotBlank(gI.getAutoResponseTemplate())) {
				GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
						.findbyTemplateName(gI.getAutoResponseTemplate());
				if (gEmailTemplate != null) {
					GroupMember gm = null;
					if (StringUtils.isNotBlank(serialNumber)) {
						try {
							gm = groupMembersService.findById(serialNumber);
						}

						catch (Exception ex) {
							log.error("Unable to find a member for serial Number : "
									+ serialNumber);
						}
					}
					try {
						Map<String, Object> modeler = new HashMap<String, Object>();
						modeler.put("groupMember", registerInterest);
						GroupEmail groupEmail = new GroupEmail();
						groupEmail.setEmailAddress(registerInterest
								.getPrimaryEmail());

						groupEmail.setSubject(gEmailTemplate.getSubject());
						groupEmail.setFromAlias(gEmailTemplate.getFromAlias());
						groupEmail.setFromAliasPersonalString(gEmailTemplate
								.getFromAliasPersonalString());
						groupEmail.setHtml(gEmailTemplate.isHtml());
						groupEmail.setReplyToEmail(gEmailTemplate
								.getReplyToEmail());
						groupEmail.setEmailAccountCode(gEmailTemplate
								.getEmailAccountCode());
						/*
						 * If there are any attachments, just add it to the
						 * email Object now
						 */
						groupEmail.setAttachments(gEmailTemplate
								.getAttachments());
						groupEmail.setGroupMember(gm);
						// groupEmail.setCreatedBy(jobCode);
						groupEmail.setCreatedAt(new Date());
						/*
						 * intermittently set the held to true so that other
						 * batches cannot pick this email until the body is
						 * actually updated
						 */
						groupEmail.setEmailHeld(true);
						groupEmail.setExpressEmail(gEmailTemplate
								.isExpressEmail()
								&& StringUtils.isBlank(gEmailTemplate
										.getAttachments()));
						groupEmail.setBody(gEmailTemplate.getTemplateName());
						/*
						 * Insert email here so that Email ID is obtained which
						 * can be used for email Tracking purpose
						 */
						GroupEmail newEmail = groupEmailsService
								.insert(groupEmail);
						modeler.put("groupEmail", newEmail);
						newEmail.setBody(mailSenderUntypedActor
								.prepareEmailBody(
										gEmailTemplate.getTemplateName(),
										modeler));
						newEmail.setEmailHeld(!registerInterest
								.isPrimaryEmailVerified());
						groupEmailsService.update(newEmail);
						registerInterest.setAutoResponseEmailSent(!newEmail
								.isEmailHeld());
						successMessage = "Thank you for registering your interest. You will receive an automated email shortly with further instructions. Please ensure you have provided us a valid email address.";
					} catch (Exception e) {
						log.info("Error in creating email for Registered Interest");
					}
				}
			}
		}

		registerInterest = registerInterestService
				.insertOrUpdate(registerInterest);
		model.addAttribute("success", true);
		if (StringUtils.isBlank(successMessage)) {
			successMessage = "Thank You for registering your interest. Someone from the team will get back to you soon or you will receive a notification from MKC shortly.";
		}
		model.addAttribute("successMessage", successMessage);
		model.addAttribute("registerInterest", registerInterest);
		List<GroupInterests> groupInterests = groupInterestService
				.findByGroupCode(registerInterest.getGroupCode(), false);
		boolean interestExists = false;
		for (GroupInterests groupInterest : groupInterests) {
			if (groupInterest.getInterestType().equalsIgnoreCase(
					registerInterest.getInterestType())) {
				interestExists = true;
				break;
			}
		}
		if (!interestExists) {
			GroupInterests newGe = new GroupInterests();
			newGe.setInterestType(registerInterest.getInterestType());
			groupInterests.add(newGe);
		}
		model.addAttribute("groupInterests", groupInterests);
		model.addAttribute("serialNumber", serialNumber);
		return "registerInterest";
	}


	private boolean isInActive(String groupCode, String interestType) {
		boolean isActive = false;
		boolean isInActive = false;
		List<GroupInterests> groupInterests = groupInterestService
				.findAllByInterestType(groupCode, interestType, true);
		for (GroupInterests groupInterest : groupInterests) {
			if (isValidStartDate(groupInterest.getStartDate())
					&& isValidEndDate(groupInterest.getExpiryDate())) {
				isActive = true;

			} else {
				isInActive = true;
			}
		}
		return (!isActive && isInActive);
	}

	private boolean isValidStartDate(Date startDate) {
		return (startDate == null || startDate.before(Calendar.getInstance()
				.getTime()));

	}

	private boolean isValidEndDate(Date endDate) {
		return (endDate == null || endDate.after(Calendar.getInstance()
				.getTime()));

	}

	private boolean interestExists(String groupCode, boolean includeExpired,
			String interestType) {
		List<GroupInterests> groupInterests = groupInterestService
				.findByGroupCode(groupCode, includeExpired);
		boolean interestExists = false;
		for (GroupInterests groupInterest : groupInterests) {
			if (groupInterest.getInterestType().equalsIgnoreCase(interestType)) {
				interestType = groupInterest.getInterestType();
				interestExists = true;
				break;
			}
		}
		return interestExists;
	}

	@RequestMapping(value = "/postSendGridEvent", method = RequestMethod.POST)
	public @ResponseBody String postSendGridEvent(
			@RequestBody List<LinkedHashMap<String, String>> sendGridEntities, @PathVariable String groupCode) {
		for (LinkedHashMap<String, String> hmap : sendGridEntities) {
			log.info("SendGrid Response - Keys:"+hmap.keySet()+" Values:"+hmap.values());
			if(hmap.containsKey("event"))
			{
				String eventType= hmap.get("event");
				if(StringUtils.isNotBlank(eventType))
				{
					//if(StringUtils.isNotBlank(props.eventTypeCategories) && props.eventTypeCategories.indexOf(eventType)!=-1)
					//{
						
						log.info("Event Type:"+eventType);
						log.info("Email Address:"+hmap.get("email"));
						AuditLog alog = new AuditLog();
						alog.setAccessDate(Calendar.getInstance().getTime());
						alog.setGroupCode(groupCode);
						alog.setCreatedAt(Calendar.getInstance().getTime());
						alog.setMethod(RequestMethod.POST.toString());
						alog.setRequestParams(hmap.get("email"));
						alog.setRequestURI(eventType);
						alog.setRequestURL("/postSendGridEvent");
						alog.setUpdatedAt(Calendar.getInstance().getTime());
						alog.setClazz("Email Tracking Mechanism");
						alog.setUserAgent("Email Tracker from SendGrid");
						try{
							loggerService.insertOrUpdate(alog);
						} catch (Exception ex) {
							log.error("Unable to create entry into Audit Log");
						}
					//}
				}
			}
			
		}
		
		return "success";
	}
}
