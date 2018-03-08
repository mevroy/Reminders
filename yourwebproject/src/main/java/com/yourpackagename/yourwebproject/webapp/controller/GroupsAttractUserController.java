/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.exception.auth.UserPermissionException;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupInboundSMS;
import com.yourpackagename.yourwebproject.model.entity.GroupInterests;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.RegisterInterest;
import com.yourpackagename.yourwebproject.model.entity.SmsApiResponseEntity;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupEmailActivityService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassCategoryService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupInboundSMSService;
import com.yourpackagename.yourwebproject.service.GroupInterestService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.GroupSMSService;
import com.yourpackagename.yourwebproject.service.GroupsService;
import com.yourpackagename.yourwebproject.service.LoggerService;
import com.yourpackagename.yourwebproject.service.RegisterInterestService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@EnableLogging(loggerClass = "GroupsAttractUserController")
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
		Role.SUPER_USER, Role.USER, Role.ANONYMOUS })
public class GroupsAttractUserController extends BaseWebAppController {
	private @Autowired RegisterInterestService registerInterestService;
	private @Autowired GroupInterestService groupInterestService;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupEmailsService groupEmailsService;
	private @Autowired GroupEmailActivityService groupEmailActivityService;
	private @Autowired GroupEventInviteService groupEventInviteService;
	private @Autowired GroupMembersService groupMembersService;
	private @Autowired MailSenderUntypedActor mailSenderUntypedActor;
	private @Autowired LoggerService loggerService;
	private @Autowired GroupEventPassesService groupEventPassesService;
	private @Autowired GroupSMSService groupSMSService;
	private @Autowired GroupInboundSMSService groupInboundSMSService;
	private @Autowired GroupsService groupsService;
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupEventInviteRSVPService groupEventInviteRSVPService;
	private @Autowired GroupEventPassCategoryService groupEventPassCategoryService;
	protected @Autowired Props props;
	
	private Logger log = LoggerFactory
			.getLogger(GroupsAttractUserController.class);

	@RequestMapping(value = "/registerInterest", method = { RequestMethod.GET,
			RequestMethod.HEAD })
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
			boolean atleast1EmailSent = false;
			boolean invitePresent = false;
			try {
				gI = groupInterestService.findByInterestType(
						registerInterest.getGroupCode(),
						registerInterest.getInterestType(), false);
			} catch (Exception e) {
			}
			if (gI != null && gI.isSendAutoResponse()) {

				List<GroupMember> registeredGroupMembers = groupMembersService
						.findByAssociatedEmailForGroupMember(
								registerInterest.getPrimaryEmail(),
								registerInterest.getGroupCode());

				GroupEmailTemplate gEmailTemplate = null;
				if (StringUtils
						.isNotBlank(gI.getDefaultResponseTemplate())) {
					gEmailTemplate = groupEmailTemplateService
							.findbyTemplateName(gI.getDefaultResponseTemplate());
				}
				/*
				 * if (StringUtils.isNotBlank(gI.getAutoResponseTemplate())) {
				 * gEmailTemplate = groupEmailTemplateService
				 * .findbyTemplateName(gI.getAutoResponseTemplate()); }
				 */
				if (gEmailTemplate != null) {
					// GroupMember gm = null;
					for (GroupMember gm : registeredGroupMembers) {
						invitePresent = false;
						if (StringUtils.isNotBlank(gm.getMemberCategoryCode())
								&& StringUtils
										.isNotBlank(props.registerInterestExcludeCategories)
								&& props.registerInterestExcludeCategories
										.indexOf(gm.getMemberCategoryCode()) > -1) {
							log.info("Skipping " + gm.getFirstName() + " "
									+ gm.getLastName()
									+ " as it is an excluded member category:"
									+ gm.getMemberCategoryCode());
						} else {
							gm.setFirstName(registerInterest.getFirstName());
							gm.setLastName(registerInterest.getLastName());
							/*
							 * if (StringUtils.isNotBlank(serialNumber)) { try {
							 * gm = groupMembersService.findById(serialNumber);
							 * }
							 * 
							 * catch (Exception ex) { log.error(
							 * "Unable to find a member for serial Number : " +
							 * serialNumber); } }
							 */
							try {
								Map<String, Object> modelMap = new HashMap<String, Object>();
								modelMap.put("groupMember", gm);
								modelMap.put("groupInterest", gI);
								 
								if (StringUtils.isNotBlank(gI.getEventCode())) {
									GroupEvents ge = groupEventsService
											.findByGroupEventCode(gI
													.getEventCode());
									modelMap.put("groupEvent", ge);
									GroupEventInvite gei = groupEventInviteService
											.findByGroupMemberAndGroupEvent(gm,
													ge);
									if (gei != null) {
										invitePresent = true;
										modelMap.put("groupEventInvite", gei);


										List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
												.findByGroupEventInvite(gei);
										if (tempList != null
												&& tempList.size() > 0) {
											modelMap.put(
													"groupEventInviteRSVP",
													tempList.get(0));
										}
										// modelMap.put("groupEventPaymentTransaction",
										// gepT);
										// modelMap.put("groupEventPasses",
										// groupPasses);
									}
								}
								if (invitePresent
										&& StringUtils.isNotBlank(gI
												.getAutoResponseTemplate())) {
									GroupEmailTemplate eventEmailTemplate = groupEmailTemplateService
											.findbyTemplateName(gI
													.getAutoResponseTemplate());
									if (eventEmailTemplate != null) {
										gEmailTemplate = eventEmailTemplate;
									}
								}
								// Template Positioning has to be here to choose
								// between event template or defaults ROI
								// template.
								modelMap.put("groupEmailTemplate",
										gEmailTemplate);
								GroupEmail groupEmail = new GroupEmail();
								try {
									groupEmail = groupEmailsService
											.createEmail(groupEmail, modelMap);
									

								} catch (Exception e) {
									groupEmail = null;
									log.error("Unable to create Completion email");
								}
								if(groupEmail!=null){
								atleast1EmailSent = true;
								registerInterest
										.setAutoResponseEmailSent(!groupEmail
												.isEmailHeld());
								successMessage = "Thank you for registering your interest! You will receive an automated email shortly (generally 2 - 10 mins) with further instructions. Please ensure you have provided us a valid email address and also make sure to check your spam/promotions/social email folders as well since the email sometimes gets redirected.";
								}
								//break;
								/*
								 * groupEmail.setEmailAddress(registerInterest
								 * .getPrimaryEmail());
								 * 
								 * groupEmail.setSubject(gEmailTemplate.getSubject
								 * ()) ; groupEmail.setFromAlias(gEmailTemplate.
								 * getFromAlias ());
								 * groupEmail.setFromAliasPersonalString
								 * (gEmailTemplate
								 * .getFromAliasPersonalString());
								 * groupEmail.setHtml(gEmailTemplate.isHtml());
								 * groupEmail.setReplyToEmail(gEmailTemplate
								 * .getReplyToEmail());
								 * groupEmail.setEmailAccountCode(gEmailTemplate
								 * .getEmailAccountCode());
								 * 
								 * If there are any attachments, just add it to
								 * the email Object now
								 * 
								 * groupEmail.setAttachments(gEmailTemplate
								 * .getAttachments());
								 * groupEmail.setGroupMember(gm); //
								 * groupEmail.setCreatedBy(jobCode);
								 * groupEmail.setCreatedAt(new Date());
								 * 
								 * intermittently set the held to true so that
								 * other batches cannot pick this email until
								 * the body is actually updated
								 * 
								 * groupEmail.setEmailHeld(true);
								 * groupEmail.setExpressEmail(gEmailTemplate
								 * .isExpressEmail() &&
								 * StringUtils.isBlank(gEmailTemplate
								 * .getAttachments()));
								 * groupEmail.setBody(gEmailTemplate
								 * .getTemplateName()); if
								 * (gEmailTemplate.isPrefillAttachments() &&
								 * StringUtils.isNotBlank(gEmailTemplate
								 * .getAttachments())) { GroupMember groupMember
								 * = new GroupMember();
								 * BeanUtils.copyProperties(groupMember,
								 * registerInterest); ObjectMapper om = new
								 * ObjectMapper(); groupEmail.setPrefillData(om
								 * .writeValueAsString(groupMember)); }
								 * 
								 * Insert email here so that Email ID is
								 * obtained which can be used for email Tracking
								 * purpose
								 * 
								 * GroupEmail newEmail = groupEmailsService
								 * .insert(groupEmail); GroupEmailActivity
								 * groupEmailActivity = new
								 * GroupEmailActivity(); groupEmailActivity
								 * .setEmailActivity(EmailActivity.CREATE);
								 * groupEmailActivity.setActivityTime(groupEmail
								 * .getCreatedAt());
								 * groupEmailActivity.setActivityBy
								 * (registerInterest .getFirstName() + " " +
								 * registerInterest.getLastName());
								 * groupEmailActivity.setGroupEmail(newEmail);
								 * groupEmailActivityService
								 * .insert(groupEmailActivity);
								 * modeler.put("groupEmail", newEmail);
								 * newEmail.setBody(mailSenderUntypedActor
								 * .prepareEmailBody(
								 * gEmailTemplate.getTemplateName(), modeler));
								 * newEmail.setEmailHeld(!registerInterest
								 * .isPrimaryEmailVerified());
								 * groupEmailsService.update(newEmail);
								 * GroupEmailActivity groupEmailActivity2 = new
								 * GroupEmailActivity(); groupEmailActivity2
								 * .setEmailActivity(EmailActivity.UPDATE);
								 * groupEmailActivity2.setActivityTime(Calendar
								 * .getInstance().getTime());
								 * groupEmailActivity2.setActivityBy
								 * (registerInterest .getFirstName() + " " +
								 * registerInterest.getLastName());
								 * groupEmailActivity2.setGroupEmail(newEmail);
								 * groupEmailActivityService
								 * .insert(groupEmailActivity2);
								 */
							} catch (Exception e) {
								log.info("Error in creating email for Registered Interest");
							}
						}
					}

					if (!atleast1EmailSent) {
						log.info("No groupMember found or no Invite Associated was found. Send a default template");
						Map<String, Object> modelMap = new HashMap<String, Object>();
						GroupMember groupMember = new GroupMember();
						BeanUtils.copyProperties(groupMember, registerInterest);
						modelMap.put("groupMember", groupMember);
						modelMap.put("groupInterest", gI);
						modelMap.put("groupEmailTemplate", groupEmailTemplateService
								.findbyTemplateName(gI.getDefaultResponseTemplate()));
						GroupEmail groupEmail = new GroupEmail();
						try {
							groupEmail = groupEmailsService.createEmail(
									groupEmail, modelMap);
							

						} catch (Exception e) {
							groupEmail = null;
							log.error("Unable to create Completion email");
						}
						if(groupEmail!=null){
							atleast1EmailSent = true;
						registerInterest.setAutoResponseEmailSent(!groupEmail
								.isEmailHeld());
						successMessage = "Thank you for registering your interest! You will receive an automated email shortly (generally 2 - 10 mins) with further instructions. Please ensure you have provided us a valid email address and also make sure to check your spam/promotions/social email folders as well since the email sometimes gets redirected.";
						}
					}
				}

			}
			String body = "ROI for " + registerInterest.getGroupCode() + "\n";
			String user = (("Name:" + registerInterest.getFirstName() + " " + registerInterest
					.getLastName()).length() > 25) ? ("Name:"
					+ registerInterest.getFirstName() + " " + registerInterest
					.getLastName()).substring(0, 25) : ("Name:"
					+ registerInterest.getFirstName() + " " + registerInterest
					.getLastName());
			user += "\nInterest:" + registerInterest.getInterestType() + "\n";
			user += "Contact:" + registerInterest.getMobilephone() + "\n";
			user += "Auto R:"+ (atleast1EmailSent?"Y"+(invitePresent?"(W/ Invite)":"(W/O Invite)"):"N");
			Groups group = groupsService.findByGroupCode(registerInterest
					.getGroupCode());
			if (group != null
					&& StringUtils.isNotBlank(group.getContactNumber())) {

				for (String phoneumber : CommonUtils.convertStringToList(
						group.getContactNumber(), ",")) {
					GroupSMS groupSMS = new GroupSMS();
					groupSMS.setMobileNumber(phoneumber);
					groupSMS.setBody(body + user);
					groupSMS.setGroupCode(group.getGroupCode());
					groupSMSService.insert(groupSMS);
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
			if (CommonUtils.isValidDates(groupInterest.getStartDate(),
					groupInterest.getExpiryDate())) {
				isActive = true;

			} else {
				isInActive = true;
			}
		}
		return (!isActive && isInActive);
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

	@RequestMapping(value = "/postSmsReplyEvent", method = RequestMethod.POST)
	public @ResponseBody String postSmsReplyEvent(
			@RequestBody SmsApiResponseEntity   smsApiResponseEntity,
			@PathVariable String groupCode) {

		log.info(smsApiResponseEntity.toString());

		if (smsApiResponseEntity != null) {
			String mid = StringUtils.replace(smsApiResponseEntity.getMessageId(), "CDATA[-", "");
			if (StringUtils.isNotBlank(mid)) {
				try {
					GroupSMS gsms = groupSMSService
							.findByMessageId(mid);
					if (gsms != null) {

						GroupInboundSMS gIbSMS = new GroupInboundSMS();
						BeanUtils.copyProperties(gIbSMS, gsms);
						gIbSMS.setCreatedAt(Calendar.getInstance().getTime());
						gIbSMS.setUpdatedAt(Calendar.getInstance().getTime());
						gIbSMS.setSmsContent(smsApiResponseEntity.getBody());
						groupInboundSMSService.insert(gIbSMS);
					}
				} catch (Exception e) {
					log.error("Error Occure when processing SMS call back");
				}
			}
		}
		return "success";
	}

	
	@RequestMapping(value = "/postSmsStatusEvent", method = RequestMethod.POST)
	public @ResponseBody String postSmsStatusEvent(
			@RequestBody SmsApiResponseEntity   smsApiResponseEntity,
			@PathVariable String groupCode) {

		log.info(smsApiResponseEntity.toString());

		if (smsApiResponseEntity != null) {
			if (StringUtils.isNotBlank(smsApiResponseEntity.getMessageId())) {
				try {
					GroupSMS gsms = groupSMSService
							.findByMessageId(smsApiResponseEntity
									.getMessageId());
					if (gsms != null) {
						gsms.setSmsDeliveredDate(smsApiResponseEntity.getReceivedTimestamp());
						gsms.setUpdatedAt(Calendar.getInstance().getTime());
						gsms.setUpdatedBy("SMS Callback Api");
						groupSMSService.update(gsms);
					}
				} catch (Exception e) {
					log.error("Error Occure when processing SMS call back");
				}
			}
		}
		return "success";
	}
	
	
	@RequestMapping(value = "/postSendGridEvent", method = RequestMethod.POST)
	public @ResponseBody String postSendGridEvent(
			@RequestBody List<LinkedHashMap<String, Object>> sendGridEntities,
			@PathVariable String groupCode) {
		for (LinkedHashMap<String, Object> hmap : sendGridEntities) {
			log.info("SendGrid Response - Keys:" + hmap.keySet() + " Values:"
					+ hmap.values());
			if (hmap.containsKey("event")) {
				String eventType = (String) hmap.get("event");
				String groupEmailId = null;
				if (hmap.containsKey("groupEmailId"))
					groupEmailId = (String) hmap.get("groupEmailId");
				if (StringUtils.isNotBlank(eventType)) {
					AuditLog alog = new AuditLog();
					alog.setAccessDate(Calendar.getInstance().getTime());
					alog.setGroupCode(groupCode);
					alog.setCreatedAt(Calendar.getInstance().getTime());
					alog.setMethod(RequestMethod.POST.toString());
					alog.setRequestParams((String) hmap.get("email"));
					alog.setRequestURI(eventType);
					alog.setRequestURL("/postSendGridEvent");
					alog.setUpdatedAt(Calendar.getInstance().getTime());
					alog.setClazz("Email Tracking Mechanism");
					alog.setUserAgent("Email Tracker from SendGrid");
					alog.setRequestIp((String) hmap.get("ip"));
					try {
						loggerService.insertOrUpdate(alog);
					} catch (Exception ex) {
						log.error("Unable to create entry into Audit Log");
					}
					// }

					if (StringUtils.isNotBlank(groupEmailId)) {
						try {
							GroupEmail groupEmail = groupEmailsService
									.findById(groupEmailId);
							log.info("Email Found with ID - " + groupEmailId);

							GroupEmailActivity gea = new GroupEmailActivity();
							gea.setActivityTime(Calendar.getInstance()
									.getTime());
							if (hmap.containsKey("timestamp")) {
								Object ts = hmap.get("timestamp");
								int timeStampInt = (Integer) hmap
										.get("timestamp");
								if (timeStampInt > 0) {
									try {

										long timestamp = Long
												.valueOf(timeStampInt);
										Date activityTime = new Date(
												timestamp * 1000);
										gea.setActivityTime(activityTime);
									} catch (Exception e) {
										log.error("Time stamp parsing Exception:"
												+ hmap.get("timestamp"));
									}
								}
							}

							gea.setActivityBy(StringUtils
									.trimToEmpty((String) hmap.get("email")));
							gea.setGroupEmail(groupEmail);
							if ("bounce".equalsIgnoreCase(eventType)) {
								gea.setEmailActivity(EmailActivity.BOUNCED);
							} else if ("deferred".equalsIgnoreCase(eventType)) {
								gea.setEmailActivity(EmailActivity.DEFERRED);
							} else if ("delivered".equalsIgnoreCase(eventType)) {
								gea.setEmailActivity(EmailActivity.DELIVERED);
							} else if ("dropped".equalsIgnoreCase(eventType)) {
								gea.setEmailActivity(EmailActivity.DROPPED);
							} else if ("processed".equalsIgnoreCase(eventType)) {
								gea.setEmailActivity(EmailActivity.PROCESSED);
							} else if ("click".equalsIgnoreCase(eventType)) {
								gea.setEmailActivity(EmailActivity.CLICKED);
							} else if ("open".equalsIgnoreCase(eventType)) {
								gea.setEmailActivity(EmailActivity.OPENED);
							} else {
								gea.setEmailActivity(EmailActivity.UPDATE);
							}
							groupEmailActivityService.insert(gea);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("No Email found for ID - " + groupEmailId);
						}
					}
				}
			}

		}

		return "success";
	}

	@RequestMapping(value = "/json/viewSoldTickets", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPass> viewSoldTickets(Model model,
			@PathVariable String groupCode,
			@RequestParam(required = true) String groupEventCode,
			HttpServletRequest request) throws Exception {
		return groupEventPassesService
				.findSoldTicketsByGroupCodeAndGroupEventCode(groupCode,
						groupEventCode);
	}
	
	@RequestMapping(value = "/json/viewSoldTicketsForCategory", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPass> viewSoldTicketsForCategory(Model model,
			@PathVariable String groupCode,
			@RequestParam(required = true) String groupEventCode, @RequestParam(required = true) String groupEventPassCategoryId,
			HttpServletRequest request) throws Exception {
		GroupEventPassCategory groupEventPassCategory = groupEventPassCategoryService.findById(groupEventPassCategoryId);
		return groupEventPassesService
				.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(groupCode, groupEventCode, groupEventPassCategory);
	}

	@RequestMapping(value = { "/seating", "/Seating", "SEATING" }, method = RequestMethod.GET)
	public String seating(Locale locale, Model model, @RequestParam(required = false) String autoRegister) {

		model.addAttribute("autoRegister", autoRegister);
		return "seatingSearch";
	}

	@RequestMapping(value = "/seating", method = { RequestMethod.POST })
	public String loadSeating(Locale locale, Model model,
			@RequestParam(required = false) String value, @RequestParam(required = false) String autoRegister) {
		model.addAttribute("autoRegister", autoRegister);
		if (StringUtils.isBlank(value)) {
			return this.seating(locale, model, autoRegister);
		}
		List<GroupEventPass> geps = new ArrayList<GroupEventPass>();
		try {
			GroupEventPass gep = groupEventPassesService
					.findByPassBarcode(value);
			if (gep != null) {
				geps.add(gep);
				if (gep.getGroupEventInvite() != null) {
					GroupEventInvite gei = gep.getGroupEventInvite();
					geps = groupEventPassesService.findByGroupEventInvite(gei);
					model.addAttribute("groupEventInvite", gei);
					model.addAttribute("groupMember", gei.getGroupMember());
				}
				model.addAttribute("groupEventPasses", geps);

				for (GroupEventPass gepU : geps) {
					if (CommonUtils.isValidDates(gepU.getPassStartDate(),
							gepU.getPassExpiryDate())
							&& gepU.getTrackingDate() == null && StringUtils.isNotBlank(autoRegister) && "true".equalsIgnoreCase(autoRegister)) {
						gepU.setUpdatedAt(Calendar.getInstance().getTime());
						gepU.setTrackingDate(Calendar.getInstance().getTime());
						gepU.setUpdatedBy("Self Registration");
						groupEventPassesService.update(gepU);
					}
				}
			} else {
				addError("The requested Ticket ID " + value + " is invalid.",
						model);
			}
		} catch (Exception e) {

		}
		return "seatingSearch";
	}

	@RequestMapping(value = "/generateMembershipFormPDF", method = { RequestMethod.POST })
	public void generateMembershipFormPDF(@PathVariable String groupCode,
			@RequestBody GroupMember groupMember, ModelMap model,
			HttpServletResponse response) throws Exception {

		try {

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			output = groupMembersService.prefillPDF(groupMember);

			response.addHeader("Content-Type", "application/pdf");
			response.addHeader(
					"Content-Disposition",
					"attachment; filename=\""
							+ groupMember.getSerialNumber().substring(0, 5)
									.toUpperCase() + "_"
							+ StringUtils.upperCase(groupMember.getFirstName())
							+ ".pdf\"");
			response.getOutputStream().write(output.toByteArray());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/generateMembershipFormPDF", method = { RequestMethod.GET })
	public void generateMembershipFormPDFByID(@PathVariable String groupCode,
			@RequestParam String serialNumber, ModelMap model,
			HttpServletResponse response) throws Exception {

		try {
			GroupMember groupMember = groupMembersService
					.findById(serialNumber);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			output = groupMembersService.prefillPDF(groupMember);

			response.addHeader("Content-Type", "application/pdf");
			response.addHeader(
					"Content-Disposition",
					"attachment; filename=\""
							+ groupMember.getSerialNumber().substring(0, 5)
									.toUpperCase() + "_"
							+ StringUtils.upperCase(groupMember.getFirstName())
							+ ".pdf\"");
			response.getOutputStream().write(output.toByteArray());

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("No Member details found for ID - " + serialNumber);
		}

	}

	@RequestMapping(value = "/generatePasses", method = RequestMethod.GET)
	public void generatePasses(
			Locale locale,
			Model model,
			@RequestParam(required = true) String groupEventInviteIdOrEventCode,
			HttpServletResponse response) {

		try {
			GroupEventInvite gei = groupEventInviteService
					.findByGroupEventInviteCode(groupEventInviteIdOrEventCode);
			if (gei == null) {
				gei = groupEventInviteService
						.findById(groupEventInviteIdOrEventCode);
			}
			List<GroupEventPass> groupEventPasses = groupEventPassesService
					.findApprovedPassesByGroupEventInvite(gei);
			if (CollectionUtils.isNotEmpty(groupEventPasses)) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				output = groupEventInviteService.generateTicketPDF(gei,
						groupEventPasses);
				response.addHeader("Content-Type", "application/pdf");
				response.addHeader(
						"Content-Disposition",
						"attachment; filename=\"TICKETS_"
								+ StringUtils.upperCase(gei.getGroupMember()
										.getFirstName()) + ".pdf\"");
				response.getOutputStream().write(output.toByteArray());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "/loadEmailWebversion", method = RequestMethod.GET)
	public String loadEmailWebversion(Model model, @PathVariable String groupCode,
			@RequestParam(required = true) String groupEmailId, HttpServletRequest request)
			throws Exception {
		try{
		GroupEmail groupEmail = groupEmailsService.findById(groupEmailId);
		if (groupEmail != null) {
			model.addAttribute("emailHTML", groupEmail.getBody());
			if (groupEmail.getEmailViewedDate() == null) {
				groupEmail.setEmailViewedDate(Calendar.getInstance().getTime());
				groupEmail.setUpdatedAt(Calendar.getInstance().getTime());
				groupEmailsService.update(groupEmail);
				if (!StringUtils.isBlank(groupEmail.getGroupEventInviteId())) {
					GroupEventInvite gei = groupEventInviteService
							.findById(groupEmail.getGroupEventInviteId());
					if (gei != null && !gei.isInviteDelivered()) {
						gei.setInviteDelivered(true);
						gei.setUpdatedAt(Calendar.getInstance().getTime());
						groupEventInviteService.update(gei);
					}
				}
			}
		}
		}
		catch(Exception e)
		{
			throw new UserPermissionException("Oops! Looks like you are trying to dig into an old email. This email has been archived! Sorry about that.");
		}
		return "loadEmailWebversion";
	}
	
	@RequestMapping(value = "/json/checkAttendance", method = RequestMethod.GET)
	public @ResponseBody int checkAttendance(Model model, @PathVariable String groupCode,
			@RequestParam(required = true) String groupEventPassCategoryId, HttpServletRequest request)
			throws Exception {
		try{
			return groupEventPassesService.checkPassAttendance(groupEventPassCategoryId);
		}
		catch(Exception e)
		{
			
		}
		return 0;
	}
	
	@RequestMapping(value = "/json/checkTotalAttendance", method = RequestMethod.GET)
	public @ResponseBody int checkTotalAttendance(Model model, @PathVariable String groupCode,
			@RequestParam(required = true) String groupEventCode, HttpServletRequest request)
			throws Exception {
		try{
			return groupEventPassesService.checkTotalEventAttendance(groupEventCode);
		}
		catch(Exception e)
		{
			
		}
		return 0;
	}
	@RequestMapping(value = "/json/checkAuditLogs", method = RequestMethod.GET)
	public @ResponseBody List<AuditLog> checkAuditLogs(Model model, @PathVariable String groupCode,
			@RequestParam(required = true) String requestURI, @RequestParam(required = true) String method,  HttpServletRequest request)
			throws Exception {
		try{
			return loggerService.findByGroupCodeAndRequestURIAndMethod(groupCode, requestURI, method);
		}
		catch(Exception e)
		{
			
		}
		return new ArrayList<AuditLog>();
	}	
}
