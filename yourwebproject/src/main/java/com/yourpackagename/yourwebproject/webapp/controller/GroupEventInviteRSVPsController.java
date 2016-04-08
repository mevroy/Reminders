/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;





import com.yourpackagename.framework.exception.auth.UserPermissionException;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupsService;


/**
 * @author mdsouza
 *
 */
@Controller
@EnableLogging(loggerClass = "GroupEventInviteRSVPsController")
@CheckPermission(allowedRoles = { Role.ANONYMOUS})
public class GroupEventInviteRSVPsController extends BaseWebAppController {

	@Autowired
	private GroupEventInviteRSVPService groupEventInviteRSVPService;
	@Autowired
	private GroupEventInviteService groupEventInviteService;
	@Autowired
	private GroupEventsService groupEventsService;
	@Autowired
	private GroupEmailsService groupEmailsService;
	@Autowired
	private MailSenderUntypedActor mailSenderUntypedActor;
	@Autowired
	private GroupEmailTemplateService groupEmailTemplateService;
	@Autowired
	private GroupsService groupsService;


	private Logger log = LoggerFactory
			.getLogger(GroupEventInviteRSVPsController.class);


	@RequestMapping(value = "/createRSVP",  method = RequestMethod.GET)
	public String createRSVPRequest(Model model,
			@RequestParam(required = true) String groupEventInviteId,
			@RequestParam(required = false) String rsvpMessage,
			@PathVariable String groupCode)
			throws Exception {
		if (StringUtils.isBlank(rsvpMessage)) {
			rsvpMessage = "RSVP";
		}
		model.addAttribute("rsvpMessage", rsvpMessage);
		GroupEventInvite gei;
		try {
			gei = groupEventInviteService.findById(groupEventInviteId);
			if (gei == null) {
				// model.addAttribute("error", true);
				// model.addAttribute("errorMessage",
				// "Sorry! You do not have a valid invite to RSVP for this event.");
				// return "createRSVP";
				throw new UserPermissionException(
						"Sorry! You do not have a valid invite to RSVP for this event.");
			}
		} catch (Exception ex) {

			model.addAttribute("error", true);
			model.addAttribute("errorMessage",
					"Sorry! You do not have a valid invite to RSVP for this event.");
			return "createRSVP";

			/*
			 * throw new UserPermissionException(
			 * "Sorry! You do not have a valid invite to RSVP for this event.");
			 */
		}
		Groups grp = groupsService.findByGroupCode(gei.getGroupCode());
		if (!gei.isInviteDelivered()) {
			gei.setInviteDelivered(true);
			gei.setUpdatedAt(Calendar.getInstance().getTime());
			groupEventInviteService.update(gei);
		}

		GroupEvents grpEvent = groupEventsService.findByGroupEventCode(gei
				.getGroupEventCode());

		List<GroupEventInviteRSVP> rsvpList = groupEventInviteRSVPService
				.findByGroupEventInvite(gei);

		GroupEventInviteRSVP ger = new GroupEventInviteRSVP();

		if (rsvpList != null && rsvpList.size() > 0) {
			ger = rsvpList.get(0);
			model.addAttribute("info", true);
			model.addAttribute(
					"infoMessage",
					"Our records indicate that you have already RSVP'd. "
							+ (!grpEvent.isPaidEvent() ? "No further action needed. "
									: "")
							+ (grpEvent.getRsvpDeadlineDate() != null
									&& grpEvent.getRsvpDeadlineDate().before(
											Calendar.getInstance().getTime()) ? "No further changes are allowed as the RSVP deadline date has crossed. "
									: ""));
		}

		ger.setGroupEventInvite(gei);
		if (grpEvent.getRsvpDeadlineDate() != null
				&& grpEvent.getRsvpDeadlineDate().before(
						Calendar.getInstance().getTime())) {
			model.addAttribute("disableButton", true);
			if (rsvpList == null || (rsvpList != null && rsvpList.size() <= 0)) {
				model.addAttribute("popupModal", true);
				model.addAttribute("popupTitle", "Oops!");
				model.addAttribute(
						"popupMessage",
						"Sorry! The RSVP deadline for this event has passed. If you still wish you RSVP, please follow the instructions in your invite email or contact your event organiser. Contact us with further questions @ "
								+ grp.getContactEmail());
			}
		}

		if (grpEvent.isPaidEvent()
				&& StringUtils.isNotBlank(gei.getTransactionReference())
				&& "true".equalsIgnoreCase(ger.getRsvpOutcome())) {
			model.addAttribute("disableButton", true);
			model.addAttribute("popupModal", true);
			model.addAttribute("popupTitle", "Status");
			model.addAttribute(
					"popupMessage",
					"We have received your Transaction Reference Number - "
							+ gei.getTransactionReference().toUpperCase()
							+ " and are processing it. No further actions can be processed on this page at this time. Once your transaction is approved, this page will show the status of your payment (usually within 2 - 3 business days). If you need to purchase additional tickets, contact us with further details @ "
							+ grp.getContactEmail());

		}

		if (grpEvent.isPaidEvent() && gei.isTransactionApproved()) {
			model.addAttribute("disableButton", true);
			model.addAttribute("popupModal", true);
			model.addAttribute("popupTitle", "Status");
			model.addAttribute(
					"popupMessage",
					"Please note that we have received and approved your payment"
							+ (gei.getPaidAmount() > 0.0 ? " of $"
									+ gei.getPaidAmount() : "")
							+ (StringUtils.isNotBlank(gei
									.getTransactionReference()) ? " for transaction reference number - "
									+ gei.getTransactionReference()
									: "")
							+ ". You should be receiving your tickets soon. No further actions can be processed on this page at this time. If you need to purchase additional tickets, contact us with further details @ "
							+ grp.getContactEmail());

		}

		model.addAttribute("groupEventInviteRSVP", ger);
		model.addAttribute("groupEvent", grpEvent);
		return "createRSVP";
	}

	@RequestMapping(value = "/saveGroupEventInviteRSVP", method = RequestMethod.POST)
	public String saveGroupEventInviteRSVP(
			Model model,
			@ModelAttribute("groupEventInviteRSVP") GroupEventInviteRSVP groupEventInviteRSVP,
			@RequestParam(required = false) String rsvpMessage)
			throws Exception {
		GroupEventInvite groupEventInvite = groupEventInviteService
				.findById(groupEventInviteRSVP.getGroupEventInvite()
						.getGroupEventInviteId());
		groupEventInvite.setRsvpd(true);
		groupEventInvite.setUpdatedAt(Calendar.getInstance().getTime());
		/*
		 * Setting the code that members can carry with them - Will have to be
		 * moved from here to create Invite location as some people may not RSVP
		 * but still turn up for the event
		 * groupEventInvite.setGroupEventInviteCode(groupEventInvite
		 * .getGroupEventInviteId().substring(0, 5).toUpperCase());
		 */
		if (StringUtils.isNotBlank(groupEventInviteRSVP
				.getTransactionReference())) {
			groupEventInvite.setTransactionReference(groupEventInviteRSVP
					.getTransactionReference().toUpperCase());
		}

		groupEventInviteRSVP.setMemberCategoryCode(groupEventInvite
				.getGroupMember().getMemberCategoryCode());
		groupEventInviteRSVP.setGroupEventCode(groupEventInvite
				.getGroupEventCode());
		groupEventInviteRSVP.setGroupCode(groupEventInvite.getGroupCode());
		groupEventInviteRSVP.setRsvpDateTime(Calendar.getInstance().getTime());
		groupEventInviteRSVP.setGroupMember(groupEventInvite.getGroupMember());
		groupEventInviteRSVP.setRsvpd(true);
		groupEventInviteRSVP.setGroupEventInvite(groupEventInvite);

		GroupEventInviteRSVP geiR = groupEventInviteRSVPService
				.insert(groupEventInviteRSVP);
		groupEventInviteService.update(groupEventInvite);

		GroupEvents grpEvent = groupEventsService
				.findByGroupEventCode(groupEventInvite.getGroupEventCode());
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (grpEvent != null && grpEvent.isAutoResponseForRSVPAllowed()
				&& !StringUtils.isBlank(grpEvent.getAutoResponseRSVPTemplate())) {
			GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
					.findbyTemplateName(grpEvent.getAutoResponseRSVPTemplate());
			if (gEmailTemplate != null) {
				GroupMember groupMember = groupEventInvite.getGroupMember();
				modelMap.put("groupMember", groupMember);
				modelMap.put("groupEventInvite", groupEventInvite);
				modelMap.put("groupEvent", grpEvent);
				modelMap.put("groupEventInviteRSVP", geiR);
				GroupEmail groupEmail = new GroupEmail();
				groupEmail.setEmailAddress(groupMember.getPrimaryEmail());
				groupEmail.setBccEmailAddress(groupMember.getOtherEmail());
				String ccEmail = "";
				for (GroupDependents groupDependents : groupMember
						.getGroupDependents()) {
					if (StringUtils.isNotBlank(groupDependents.getEmail()))
						ccEmail += groupDependents.getEmail() + ",";
				}
				if (StringUtils.isNotBlank(ccEmail)) {
					ccEmail = ccEmail.substring(0, ccEmail.length() - 1);
					groupEmail.setCcEmailAddress(ccEmail);
				}
				groupEmail.setSubject(gEmailTemplate.getSubject());
				groupEmail.setFromAlias(gEmailTemplate.getFromAlias());
				groupEmail.setFromAliasPersonalString(gEmailTemplate
						.getFromAliasPersonalString());
				groupEmail.setHtml(gEmailTemplate.isHtml());
				groupEmail.setReplyToEmail(gEmailTemplate.getReplyToEmail());
				groupEmail.setEmailAccountCode(gEmailTemplate
						.getEmailAccountCode());
				/*
				 * If there are any attachments, just add it to the email Object
				 * now
				 */
				groupEmail.setAttachments(gEmailTemplate.getAttachments());
				// groupEmail.setCreatedBy(jobCode);
				groupEmail.setCreatedAt(new Date());
				/* set the body to Template name intermittently */
				groupEmail.setBody(gEmailTemplate.getTemplateName());
				groupEmail.setGroupMember(groupMember);
				groupEmail.setGroupEventInviteId(groupEventInvite
						.getGroupEventInviteId());
				Date emailExpdate = grpEvent.getEventDate();
				if (groupEventInvite.getInviteExpiryDate() != null
						&& emailExpdate != null
						&& emailExpdate.after(groupEventInvite
								.getInviteExpiryDate())) {
					emailExpdate = groupEventInvite.getInviteExpiryDate();
				}
				groupEmail.setEmailExpirydate(emailExpdate);
				groupEmail.setEmailingDate(groupEventInvite
						.getInviteStartDate());
				/*
				 * Intermittently set the hold email to true so that Other
				 * batches dont pick the email when the body is actually set as
				 * the template name
				 */
				groupEmail.setEmailHeld(true);
				groupEmail
						.setExpressEmail(gEmailTemplate.isExpressEmail()
								&& StringUtils.isBlank(gEmailTemplate
										.getAttachments()));
				GroupEmail newEmail = groupEmailsService.insert(groupEmail);
				modelMap.put("groupEmail", newEmail);
				newEmail.setBody(mailSenderUntypedActor.prepareEmailBody(
						gEmailTemplate.getTemplateName(), modelMap));
				newEmail.setEmailHeld(groupEventInvite.isInviteHeld()
						|| !groupMember.isPrimaryEmailVerified());
				groupEmailsService.insertOrUpdate(newEmail);
			}
		}

		model.addAttribute("groupEventInviteRSVP", geiR);
		model.addAttribute("groupEvent", grpEvent);
		model.addAttribute("success", true);
		model.addAttribute("rsvpMessage", rsvpMessage);

		if (grpEvent.isPaidEvent()
				&& StringUtils.isNotBlank(geiR.getTransactionReference())
				&& "true".equalsIgnoreCase(geiR.getRsvpOutcome())) {
			model.addAttribute("disableButton", true);
			model.addAttribute(
					"successMessage",
					"You have successfully updated your Transaction Reference Number and no further updates will be allowed to this page. Once your transaction is approved, this page will show the status of your payment (usually within 2 - 3 business days).");
		} else {
			model.addAttribute("successMessage",
					"You have successfully RSVP'd for this event. Thank you!");
		}
		return "createRSVP";
	}


}
