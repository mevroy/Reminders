/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.FeedbackService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN })
@EnableLogging(loggerClass = "GroupEventInviteController")
public class GroupEventInviteController extends BaseWebAppController {

	private @Autowired GroupMembersService groupMembersService;
	private @Autowired GroupEventInviteService groupEventInvitesService;
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupEmailsService groupEmailsService;
	// private @Autowired MailSenderActor mailSenderActor;
	private @Autowired MailSenderUntypedActor mailSenderUntypedActor;
	private @Autowired GroupEventInviteRSVPService groupEventInviteRSVPService;
	private @Autowired FeedbackService feedbackService;

	@RequestMapping(value = "/createGroupEventInvite", method = RequestMethod.GET)
	public String addGroupEvent(Locale locale, Model model) {
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "addGroupEventInvite";
	}

	@RequestMapping(value = "/json/saveGroupEventInvite", method = RequestMethod.POST)
	public @ResponseBody String createGroupEvent(Locale locale, Model model,
			@RequestBody List<LinkedHashMap<String, String>> rowDataObjects,
			@PathVariable String groupCode,
			@RequestParam String groupEventCode,
			@RequestParam String memberCategoryCode,
			@RequestParam(required = false) Date inviteStartDate,
			@RequestParam(required = false) Date inviteExpiryDate,
			@RequestParam(required = false) boolean inviteHeld)
			throws Exception {

		List<GroupMember> gm = new ArrayList<GroupMember>();
		GroupEventInvite groupEventInvite = new GroupEventInvite();
		// groupEventInvite.setMemberCategoryCode(memberCategoryCode);
		groupEventInvite.setGroupEventCode(groupEventCode);
		groupEventInvite.setInviteStartDate(inviteStartDate);
		groupEventInvite.setInviteExpiryDate(inviteExpiryDate);
		groupEventInvite.setInviteHeld(inviteHeld);
		GroupEvents ge = groupEventsService
				.findByGroupEventCode(groupEventCode);
		int eventCodeLength = 6;
		if (ge != null) {
			if (ge.getGroupEventInviteCodeLength() > eventCodeLength) {
				eventCodeLength = ge.getGroupEventInviteCodeLength();
			}
		}
		int count = 0;
		int failureCount = 0;
		int eventCodeCreations = 0;
		for (LinkedHashMap<String, String> hmap : rowDataObjects) {
			GroupMember groupMember = groupMembersService.findById(hmap
					.get("serialNumber"));

			try {
				groupEventInvite.setGroupMember(groupMember);
				groupEventInvite.setMemberCategoryCode(groupMember
						.getMemberCategoryCode());
				groupEventInvite.setGroupCode(groupMember.getGroupCode());
				groupEventInvite = groupEventInvitesService
						.insert(groupEventInvite);
				count++;
				if (ge != null && ge.getGroupEventInviteCodeLength() > 0) {
					groupEventInvite.setGroupEventInviteCode(groupEventInvite
							.getGroupEventInviteId()
							.substring(0, eventCodeLength).toUpperCase());
					groupEventInvitesService.update(groupEventInvite);
					eventCodeCreations++;
				}

			} catch (Exception e) {
				failureCount++;
				e.printStackTrace();
				addError(
						"An error seems to have occured while creating invites. Please contact you system administrator.",
						model);

			}
		}
		addSuccess(
				count
						+ " successful invites, "
						+ failureCount
						+ " exceptions  while processing this Invite request. Thank you!",
				model);

		return count
				+ " successful invites, "
				+ eventCodeCreations
				+ " Invite Codes, "
				+ failureCount
				+ " exceptions  while processing this Invite request. Thank you!";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupEventInvites/{groupEventCode}/{memberCategoryCode}", method = RequestMethod.POST)
	public @ResponseBody List<GroupEventInvite> viewGroupEventInvites(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String memberCategoryCode,
			@PathVariable String groupEventCode) {
		List<GroupEventInvite> gei = new ArrayList<GroupEventInvite>();
		if (!StringUtils.isBlank(memberCategoryCode)) {
			gei = groupEventInvitesService
					.findByGroupCodeAndCategoryCodeAndEventCode(groupCode,
							memberCategoryCode, groupEventCode);
		} else {
			gei = groupEventInvitesService.findByGroupCodeAndEventCode(
					groupCode, groupEventCode);
		}
		return gei;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupEventInvites/{groupEventCode}", method = RequestMethod.POST)
	public @ResponseBody List<GroupEventInvite> viewGroupEventInvites(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventCode) {
		List<GroupEventInvite> gei = new ArrayList<GroupEventInvite>();

		gei = groupEventInvitesService.findByGroupCodeAndEventCode(groupCode,
				groupEventCode);

		return gei;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewScannedGroupEventInvites", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventInvite> viewScannedGroupEventInvites(
			Locale locale, Model model, @PathVariable String groupCode,
			@RequestParam(required = false) String groupEventInviteId,
			@RequestParam(required = false) String serialNumber) {
		List<GroupEventInvite> gei = new ArrayList<GroupEventInvite>();

		if (StringUtils.isNotBlank(groupEventInviteId)
				|| StringUtils.isNotBlank(serialNumber)) {
			try {
				GroupEventInvite invite = groupEventInvitesService
						.findById(groupEventInviteId);
				gei.add(invite);
			} catch (Exception e) {

				if (StringUtils.isNotBlank(serialNumber)) {
					GroupMember gm = new GroupMember();
					try {
						gm = groupMembersService.findById(serialNumber);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					gei = groupEventInvitesService.findByGroupMember(gm);
				}
			}
		}

		return gei;
	}

	@RequestMapping(value = "/json/saveGroupEventInviteEmails", method = RequestMethod.POST)
	public @ResponseBody String saveGroupEventInvites(Locale locale,
			Model model,
			@RequestBody List<LinkedHashMap<String, String>> rowDataObjects,
			@PathVariable String groupCode, @RequestParam String templateName,
			@RequestParam String groupEventCode,
			@RequestParam String memberCategoryCode) throws Exception {

		List<GroupEventInvite> invites = new ArrayList<GroupEventInvite>();
		int emailInviteNotsent = 0;
		int emailExceptions = 0;
		Map<String, Object> modelMap = new HashMap<String, Object>();

		GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
				.findbyTemplateName(templateName);
		if (gEmailTemplate == null)
			throw new Exception(
					"Unable to locate a template for Template Name:"
							+ templateName);

		GroupEvents grpEvent = groupEventsService
				.findByGroupEventCode(groupEventCode);
		// for(Map.Entry<String, String> entry: rowDataObjects.entrySet())
		for (LinkedHashMap<String, String> hmap : rowDataObjects) {
			try {

				GroupEventInvite groupEventInvite = groupEventInvitesService
						.findById(hmap.get("groupEventInviteId"));

				if (!groupEventInvite.isInviteHeld()
						&& groupEventInvite.getGroupMember()
								.isPrimaryEmailVerified()) {
					GroupMember groupMember = groupEventInvite.getGroupMember();
					modelMap.put("groupMember", groupMember);
					modelMap.put("groupEventInvite", groupEventInvite);
					modelMap.put("groupEvent", grpEvent);
					GroupEmail groupEmail = new GroupEmail();
					groupEmail.setEmailAddress(groupMember.getPrimaryEmail());
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
					groupEmail.setBccEmailAddress(groupMember.getOtherEmail());
					groupEmail.setSubject(gEmailTemplate.getSubject());
					groupEmail.setFromAlias(gEmailTemplate.getFromAlias());
					groupEmail.setFromAliasPersonalString(gEmailTemplate
							.getFromAliasPersonalString());
					groupEmail.setHtml(gEmailTemplate.isHtml());
					groupEmail
							.setReplyToEmail(gEmailTemplate.getReplyToEmail());
					groupEmail.setEmailAccountCode(gEmailTemplate
							.getEmailAccountCode());
					/*
					 * If there are any attachments, just add it to the email
					 * Object now
					 */
					groupEmail.setAttachments(gEmailTemplate.getAttachments());
					// groupEmail.setCreatedBy(jobCode);
					groupEmail.setCreatedAt(new Date());
					/* set the body to Template name intermittently */
					groupEmail.setBody(templateName);
					groupEmail.setGroupMember(groupMember);
					groupEmail.setGroupEventInviteId(groupEventInvite
							.getGroupEventInviteId());
					Date emailExpdate = grpEvent.getExpiryDate();
					if (groupEventInvite.getInviteExpiryDate() != null
							&& emailExpdate != null
							&& emailExpdate.after(groupEventInvite
									.getInviteExpiryDate())) {
						emailExpdate = groupEventInvite.getInviteExpiryDate();
					}
					groupEmail.setEmailExpirydate(emailExpdate);
					Date emailStartDate = groupEventInvite.getInviteStartDate();
					if (emailStartDate != null && emailExpdate != null
							&& emailStartDate.after(emailExpdate)) {
						emailStartDate = new DateTime(emailExpdate.getTime())
								.minusDays(45).toDate();
					}
					groupEmail.setEmailingDate(emailStartDate);
					/*
					 * Intermittently set the hold email to true so that Other
					 * batches dont pick the email when the body is actually set
					 * as the template name
					 */
					groupEmail.setEmailHeld(true);
					groupEmail.setExpressEmail(gEmailTemplate.isExpressEmail()
							&& StringUtils.isBlank(gEmailTemplate
									.getAttachments()));
					GroupEmail newEmail = groupEmailsService.insert(groupEmail);
					modelMap.put("groupEmail", newEmail);
					newEmail.setBody(mailSenderUntypedActor.prepareEmailBody(
							templateName, modelMap));
					newEmail.setEmailHeld(groupEventInvite.isInviteHeld()
							|| !groupMember.isPrimaryEmailVerified());
					groupEmailsService.insertOrUpdate(newEmail);
					if (!newEmail.isEmailHeld()) {
						groupEventInvite.setInviteSent(true);
						groupEventInvite.setUpdatedAt(Calendar.getInstance()
								.getTime());
						groupEventInvite.setInviteEmailCount(groupEventInvite
								.getInviteEmailCount() + 1);
						groupEventInvitesService.update(groupEventInvite);
					}
					invites.add(groupEventInvite);
				} else {
					logger.info("Skipping this invite "
							+ groupEventInvite.getGroupEventCode()
							+ " as it was marked as held or sender email was not verified: Invite Held-"
							+ groupEventInvite.isInviteHeld()
							+ " && Email verified-"
							+ groupEventInvite.getGroupMember()
									.isPrimaryEmailVerified()
							+ " for Member - "
							+ groupEventInvite.getGroupMember().getFirstName()
							+ " "
							+ groupEventInvite.getGroupMember().getLastName());
					emailInviteNotsent++;
				}
			} catch (Exception e) {
				emailExceptions++;
				e.printStackTrace();
			}

		}
		String returnVal = invites.size() + " email invite(s) created , "
				+ emailInviteNotsent + " held invite(s) have been skipped and "
				+ emailExceptions + " exceptions have occured. Thank you!";
		return returnVal;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/viewGroupEventInvites", method = RequestMethod.GET)
	public String viewGroupEventInvite(Locale locale, Model model) {
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "viewGroupEventInvites";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/addGroupEventPassesToGroupMembers", method = RequestMethod.GET)
	public String addGroupEventPassesToGroupMembers(Locale locale, Model model) {
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "addGroupEventPassesToGroupMembers";
	}

	@RequestMapping(value = "/registerGroupEventInvites", method = RequestMethod.GET)
	public String registerGroupEventInvites(Locale locale, Model model) {
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "registerGroupEventInvites";
	}

	@RequestMapping(value = "/createGroupEventInviteEmails", method = RequestMethod.GET)
	public String createGroupEventInviteEmails(Locale locale, Model model) {
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "createGroupEventInviteEmails";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewLatestGroupEventInvitesRSVPs/{groupEventCode}/{memberCategoryCode}", method = RequestMethod.POST)
	public @ResponseBody List<GroupEventInviteRSVP> viewLatestGroupEventInvitesRSVPs(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String memberCategoryCode,
			@PathVariable String groupEventCode) {
		/*
		 * List<GroupEventInviteRSVP> gei = new
		 * ArrayList<GroupEventInviteRSVP>(); if
		 * (!StringUtils.isBlank(memberCategoryCode)) { gei =
		 * groupEventInviteRSVPService
		 * .findLatestRSVPsByMemberCategoryCodeAndEventCode(groupCode,
		 * memberCategoryCode, groupEventCode); } else { gei =
		 * groupEventInviteRSVPService
		 * .findLatestRSVPsByGroupCodeAndEventCode(groupCode, groupEventCode); }
		 */

		List<GroupEventInvite> gei = new ArrayList<GroupEventInvite>();
		List<GroupEventInviteRSVP> grsvps = new ArrayList<GroupEventInviteRSVP>();
		if (!StringUtils.isBlank(memberCategoryCode)) {
			gei = groupEventInvitesService
					.findByGroupCodeAndCategoryCodeAndEventCode(groupCode,
							memberCategoryCode, groupEventCode);
		} else {
			gei = groupEventInvitesService.findByGroupCodeAndEventCode(
					groupCode, groupEventCode);
		}
		for (GroupEventInvite geI : gei) {
			List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
					.findByGroupEventInvite(geI);
			if (tempList != null && tempList.size() > 0) {
				grsvps.add(tempList.get(0));
			}
		}
		return grsvps;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewLatestGroupEventInvitesRSVPs/{groupEventCode}", method = RequestMethod.POST)
	public @ResponseBody List<GroupEventInviteRSVP> viewLatestGroupEventInvitesRSVPs(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventCode) {
		/*
		 * List<GroupEventInviteRSVP> gei = new
		 * ArrayList<GroupEventInviteRSVP>(); gei =
		 * groupEventInviteRSVPService.findLatestRSVPsByGroupCodeAndEventCode
		 * (groupCode, groupEventCode);
		 */
		List<GroupEventInvite> gei = new ArrayList<GroupEventInvite>();
		List<GroupEventInviteRSVP> grsvps = new ArrayList<GroupEventInviteRSVP>();
		gei = groupEventInvitesService.findByGroupCodeAndEventCode(groupCode,
				groupEventCode);

		for (GroupEventInvite geI : gei) {
			List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
					.findByGroupEventInvite(geI);
			if (tempList != null && tempList.size() > 0) {
				grsvps.add(tempList.get(0));
			}
		}
		return grsvps;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewLatestGroupEventInvitesRSVPsByGEIId/{groupEventInviteId}", method = RequestMethod.POST)
	public @ResponseBody List<GroupEventInviteRSVP> viewLatestGroupEventInvitesRSVPsByGroupEventInviteId(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventInviteId) {

		List<GroupEventInviteRSVP> grsvps = new ArrayList<GroupEventInviteRSVP>();

		if (StringUtils.isNotBlank(groupEventInviteId)) {
			GroupEventInvite gei = new GroupEventInvite();
			try {
				gei = groupEventInvitesService.findById(groupEventInviteId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			grsvps = groupEventInviteRSVPService.findByGroupEventInvite(gei);
		}
		return grsvps;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/updateGroupEventInviteAttendance", method = RequestMethod.POST)
	public @ResponseBody String updateGroupEventInviteAttendance(
			Locale locale,
			Model model,
			@ModelAttribute("groupEventInvite") GroupEventInvite groupEventInvite,
			BindingResult results) throws Exception {
		GroupEventInvite gei = groupEventInvitesService
				.findById(groupEventInvite.getGroupEventInviteId());

		gei.setUpdatedAt(Calendar.getInstance().getTime());
		gei.setMarkAttended(groupEventInvite.isMarkAttended());
		gei.setPaidAmount(groupEventInvite.getPaidAmount());
		gei.setInviteHeld(groupEventInvite.isInviteHeld());
		gei.setTransactionDateTime(groupEventInvite.getTransactionDateTime());
		gei.setTransactionReference(groupEventInvite.getTransactionReference());
		gei.setTransactionApproved(groupEventInvite.isTransactionApproved());
		if (results.hasErrors()) {
			return "error";
		}
		try {
			GroupEventInvite addedGei = groupEventInvitesService.update(gei);
		} catch (Exception e) {
			addAlert("Updating GroupEvent Invite Failed", model);
			return "error";
		}

		return "success";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/updateRSVP", method = RequestMethod.POST)
	public @ResponseBody String updateRSVP(
			Locale locale,
			Model model,
			@ModelAttribute("groupEventInviteRSVP") GroupEventInviteRSVP groupEventInviteRSVP,
			@RequestParam(required = true) String mode,
			@RequestParam(required = false) String groupEventInviteId,
			BindingResult results) {

		if (StringUtils.equalsIgnoreCase(Key.ADD, mode)) {
			if (StringUtils.isBlank(groupEventInviteId)) {
				return "Cannot add RSVP as the Event Invite ID is not passed.";
			}
			if(groupEventInviteRSVP.getAdultCount()+groupEventInviteRSVP.getKidsCount()==0)
			{
				return "Make sure atleast one of Adult count or Kid count is provided.";
			}
			try {
				GroupEventInvite gei = groupEventInvitesService
						.findById(groupEventInviteId);
				groupEventInviteRSVP.setGroupEventInvite(gei);
				groupEventInviteRSVP.setGroupMember(gei.getGroupMember());
				groupEventInviteRSVP.setGroupCode(gei.getGroupCode());
				groupEventInviteRSVP.setGroupEventCode(gei.getGroupEventCode());
				groupEventInviteRSVP.setRsvpDateTime(Calendar.getInstance().getTime());
				groupEventInviteRSVP.setRsvpOutcome("true");
				groupEventInviteRSVP.setRsvpd(true);
				groupEventInviteRSVP.setMemberCategoryCode(gei.getMemberCategoryCode());
				groupEventInviteRSVP.setTransactionReference(gei.getTransactionReference());
				
				groupEventInviteRSVPService.insert(groupEventInviteRSVP);
				gei.setRsvpd(true);
				gei.setUpdatedAt(Calendar.getInstance().getTime());
				groupEventInvitesService.update(gei);
				return "success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "Unable to add RSVP";
			}

		} else if (StringUtils.equalsIgnoreCase(Key.UPDATE, mode)) {
			if (StringUtils.isBlank(groupEventInviteRSVP
					.getGroupEventInviteRSVPId())) {
				return "RSVP Record ID is not passed";
			}
			GroupEventInviteRSVP grsvp = new GroupEventInviteRSVP();
			try {
				grsvp = groupEventInviteRSVPService
						.findById(groupEventInviteRSVP
								.getGroupEventInviteRSVPId());
				if(groupEventInviteRSVP.getAdultCount()+groupEventInviteRSVP.getKidsCount()==0)
				{
					grsvp.setRsvpOutcome("false");
				}
				grsvp.setAdultCount(groupEventInviteRSVP.getAdultCount());
				grsvp.setKidsCount(groupEventInviteRSVP.getKidsCount());
				grsvp.setUpdatedAt(Calendar.getInstance().getTime());
				groupEventInviteRSVPService.update(grsvp);

				return "success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "RSVP Record is not found";
			}
		}
		return "Did not know what operation to peform.";

	}

}
