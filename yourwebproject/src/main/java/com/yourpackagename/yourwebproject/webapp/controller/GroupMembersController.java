/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.yourwebproject.actor.MailSenderActor;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupMemberCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.entity.RegisterInterest;
import com.yourpackagename.yourwebproject.model.entity.embedded.Address;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.FeedbackService;
import com.yourpackagename.yourwebproject.service.GroupDependentsService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupMemberCategoryService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.GroupSMSService;
import com.yourpackagename.yourwebproject.service.RegisterInterestService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
@EnableLogging(loggerClass = "GroupMembersController")
public class GroupMembersController extends BaseWebAppController {

	private Logger log = LoggerFactory.getLogger(GroupMembersController.class);
	private @Autowired GroupMembersService groupMembersService;
	private @Autowired GroupDependentsService groupDependentsService;
	private @Autowired MailSenderActor mailSenderActor;
	private @Autowired GroupMemberCategoryService groupMemberCategoryService;
	private @Autowired RegisterInterestService registerInterestService;
	private @Autowired FeedbackService feedbackService;
	private @Autowired GroupEmailsService groupEmailsService;
	private @Autowired GroupEventInviteService groupEventInviteService;
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupEventPassesService groupEventPassesService;
	private @Autowired GroupSMSService groupSMSService;

	@RequestMapping(value = { "/addGroupMember" }, method = RequestMethod.GET)
	public String addGroupMember(Locale locale, Model model,
			@PathVariable String groupCode) {
		GroupMember gm = new GroupMember();
		gm.setGroupCode(groupCode);
		gm.setAddress(new Address());
		model.addAttribute("groupMember", gm);
		return "addGroupMember";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER, Role.ANONYMOUS })
	@RequestMapping(value = { "/loadGroupMember" }, method = RequestMethod.GET)
	public String loadGroupMember(Locale locale, Model model,
			@PathVariable String groupCode,
			@RequestParam(required = false) String serialNumber) {
		GroupMember gm = new GroupMember();
		if (StringUtils.isNotBlank(serialNumber)) {
			try {
				gm = groupMembersService.findById(serialNumber);
			} catch (Exception e) {
				gm.setGroupCode(groupCode);
				gm.setAddress(new Address());
				e.printStackTrace();
			}
		}
		model.addAttribute("groupMember", gm);
		model.addAttribute("groupDependents", new GroupDependents());
		return "loadUpdateGroupMember";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER, Role.ANONYMOUS })
	@RequestMapping(value = "/json/updateGroupMember", method = RequestMethod.POST)
	public @ResponseBody String updateGroupMemberJson(Locale locale,
			Model model,
			@ModelAttribute("groupMember") GroupMember groupMember,
			BindingResult results) throws Exception {
		try {
			GroupMember gm = groupMembersService.findById(groupMember
					.getSerialNumber());

			gm.setFirstName(groupMember.getFirstName());
			gm.setLastName(groupMember.getLastName());
			gm.setBirthday(groupMember.getBirthday());
			if (!StringUtils.equalsIgnoreCase(gm.getPrimaryEmail(),
					groupMember.getPrimaryEmail())) {
				gm.setPrimaryEmailVerified(true);
			}
			gm.setPrimaryEmail(groupMember.getPrimaryEmail());
			gm.setMobilephone(groupMember.getMobilephone());
			gm.setOtherPhone(groupMember.getOtherPhone());
			gm.setAliasName(groupMember.getAliasName());

			if (results.hasErrors()) {
				return "error";
			}
			try {
				groupMember.setUpdatedAt(Calendar.getInstance().getTime());
				GroupMember addedGroupMember = groupMembersService.update(gm);
			} catch (Exception e) {
				addAlert("Updating GroupMember Failed", model);
				return "Update request failed. Please try again!";
			}
		} catch (Exception e) {
			return "Unable to locate member";
		}

		return "success";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER, Role.ANONYMOUS })
	@RequestMapping(value = "/json/updateDependent", method = RequestMethod.POST)
	public @ResponseBody String updateDependent(Locale locale, Model model,
			@ModelAttribute("groupMember") GroupMember groupMember, @RequestParam(required = false) String mode,
			BindingResult results) throws Exception {
		try {
			GroupMember gm = groupMembersService.findById(groupMember
					.getSerialNumber());

			for (GroupDependents groupDependent : groupMember
					.getGroupDependents()) {
				if (groupDependent != null
						&& StringUtils.isNotBlank(groupDependent
								.getDependentserialNumber())) {
					try {
						GroupDependents gd = groupDependentsService
								.findById(groupDependent
										.getDependentserialNumber());
						gd.setFirstName(groupDependent.getFirstName());
						gd.setLastName(groupDependent.getLastName());
						gd.setBirthday(groupDependent.getBirthday());
						gd.setEmail(groupDependent.getEmail());
						gd.setMobilephone(groupDependent.getMobilephone());
						gd.setRelationship(groupDependent.getRelationship());

						try {
							gd.setGroupCode(gm.getGroupCode());
							gd.setUpdatedAt(Calendar.getInstance().getTime());
							groupDependentsService.insertOrUpdate(gd);
						}

						catch (Exception e) {
							return "Dependent update failed. Please try again!";
						}
					} catch (Exception e) {
						return "Unable to find the dependent";
					}
				}
			}
			/*
			 * gm.setFirstName(groupMember.getFirstName());
			 * gm.setLastName(groupMember.getLastName());
			 * gm.setBirthday(groupMember.getBirthday()); if
			 * (!StringUtils.equalsIgnoreCase(gm.getPrimaryEmail(),
			 * groupMember.getPrimaryEmail())) {
			 * gm.setPrimaryEmailVerified(true); }
			 * gm.setPrimaryEmail(groupMember.getPrimaryEmail());
			 * gm.setMobilephone(groupMember.getMobilephone());
			 * gm.setOtherPhone(groupMember.getOtherPhone());
			 * 
			 * if (results.hasErrors()) { return "error"; }
			 */
			/*
			 * try { groupMember.setUpdatedAt(Calendar.getInstance().getTime());
			 * GroupMember addedGroupMember = groupMembersService .update(gm); }
			 * catch (Exception e) { addAlert("Updating GroupMember Failed",
			 * model); return "Update request failed. Please try again!"; }
			 */} catch (Exception e) {
			return "Unable to locate member";
		}

		return "success";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/viewAllGroupMember", method = RequestMethod.GET)
	public String viewAllGroupMember(Locale locale, Model model,
			@PathVariable String groupCode) {
		List<GroupMemberCategory> groupMemberCategories = groupMemberCategoryService
				.findByGroupCode(groupCode);
		model.addAttribute("groupMemberCategories", groupMemberCategories);
		return "viewGroupMembers";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/loadScanGroupMember", method = RequestMethod.GET)
	public String loadScanGroupMember(Locale locale, Model model,
			@RequestParam(required = false) String groupEventCode,
			@PathVariable String groupCode,
			@RequestParam(required = false) String membershipIdentifier) {
		GroupMember gm = new GroupMember();
		if (StringUtils.isNotBlank(membershipIdentifier)) {
			return this.scanGroupMember(locale, model, membershipIdentifier,
					groupEventCode);
		}
		gm.setGroupCode(groupCode);
		model.addAttribute("groupMember", gm);
		if (StringUtils.isNotBlank(groupEventCode)) {
			model.addAttribute("groupEvent",
					groupEventsService.findByGroupEventCode(groupEventCode));
		}
		return "viewSearchedGroupMember";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/scanGroupMember", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String scanGroupMember(Locale locale, Model model,
			@RequestParam(required = true) String value,
			@RequestParam(required = false) String groupEventCode) {

		boolean passScanned = false;
		GroupEventInvite gei = groupEventInviteService
				.findByGroupEventInviteCode(value);

		GroupMember gm = new GroupMember();
		if (gei != null) {
			gm = gei.getGroupMember();
		} else {
			log.error("Unable to find Event using findByGroupEventInviteCode with input:"
					+ value);
			try {
				gm = groupMembersService.findById(value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Could not find member using findById service with input:"
						+ value);
				try {
					gei = groupEventInviteService.findById(value);
					gm = gei.getGroupMember();
				} catch (Exception e1) {
					log.error("Unable to find Event using findById with input:"
							+ value);
					// TODO Auto-generated catch block
					gm = groupMembersService.findbyMembershipIdentifier(value);
					if (gm == null) {
						log.error("Could not find member using findbyMembershipIdentifier service with input:"
								+ value);
						GroupEventPass gep = groupEventPassesService
								.findByPassBarcode(value);
						if (gep == null) {
							gep = groupEventPassesService
									.findByPassIdentifier(value);
						}
						if (gep != null) {
							if (gep.getTrackingDate() != null) {
								if (gep.getGroupMember() != null) {
									addError(
											"Pass "
													+ gep.getPassBarcode()
													+ " has already been scanned at "
													+ CommonUtils
															.printDateInHomeTimeZone(gep
																	.getTrackingDate())
													+ ", against user, "
													+ gep.getGroupMember()
															.getFirstName()
													+ " "
													+ gep.getGroupMember()
															.getLastName()
													+ ". Please verify before proceeding!",
											model);
								} else {

									addError(
											"Pass "
													+ gep.getPassBarcode()
													+ " has already been scanned at "
													+ CommonUtils
															.printDateInHomeTimeZone(gep
																	.getTrackingDate())
													+ " against an unknown user. Please verify before proceeding!",
											model);
								}
							}
							passScanned = true;
							model.addAttribute("passScan", true);
							model.addAttribute("groupEventPass", gep);
							if (gep.getGroupEventInvite() != null) {
								gei = gep.getGroupEventInvite();
								gm = gep.getGroupEventInvite().getGroupMember();
							} else {
								if (gep.isPassInvalidated()) {
									addError(
											"Pass "
													+ gep.getPassBarcode()
													+ " is an invalid pass. This pass had a comment - "
													+ gep.getSoldBy()
													+ ". Please do not proceed without authorizing!",
											model);
								}
								log.error("Found Event Pass but its not associated to any event or member:"
										+ value);
								// Need to return here if you need to do
								// something different
							}
						} else {
							log.error("Could not find Group Event Pass using barcode input:"
									+ value);
						}
						if (gm == null
								|| StringUtils.isBlank(gm.getSerialNumber())) {
							addError(
									"Exception occured when trying to search for member with id:"
											+ value, model);

							gm = new GroupMember();
						}
					}
					// e1.printStackTrace();

				}

			}
		}

		/*
		 * Search for events only if the member was searched by member ID or
		 * serial Number
		 */
		if (StringUtils.isNotBlank(groupEventCode)) {
			GroupEvents grpevents = groupEventsService
					.findByGroupEventCode(groupEventCode);
			model.addAttribute("groupEvent", grpevents);
			if (gei == null && gm != null
					&& StringUtils.isNotBlank(gm.getSerialNumber())) {

				try {
					gei = groupEventInviteService
							.findByGroupMemberAndGroupEvent(gm, grpevents);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		if (gei != null) {
			GroupEvents gEvent = groupEventsService.findByGroupEventCode(gei
					.getGroupEventCode());

			if (gEvent != null
					&& gEvent.getEventDate() != null
					&& gEvent.getEventDate().before(
							DateTime.now().plusHours(-6).toDate())) {
				addAlert(
						"The Event \""
								+ gEvent.getEventName()
								+ "\" occured on "
								+ CommonUtils.printDateInHomeTimeZone(gEvent
										.getEventDate())
								+ " and registration window is open upto 6 hours after the event. Please verify before proceeding!",
						model);
				model.addAttribute("groupEvent", gEvent);
			} else if (gEvent != null
					&& gEvent.getEventDate() != null
					&& gEvent.getEventDate().after(
							DateTime.now().plusHours(6).toDate())) {
				addAlert(
						"The Event \""
								+ gEvent.getEventName()
								+ "\" is in future ("
								+ CommonUtils.printDateInHomeTimeZone(gEvent
										.getEventDate())
								+ ") and registration is allowed only 6 hours before the event. Please verify before proceeding!",
						model);
				model.addAttribute("groupEvent", gEvent);
			} else {
				if (gei.isMarkAttended()) {
					addInfo(gei.getGroupMember().getFirstName()
							+ " "
							+ gei.getGroupMember().getLastName()
							+ " is already registered for the event. Please verify! Amount paid for the event is "
							+ gei.getPaidAmount(), model);
				} else {
					if (!passScanned) {
						// If a pass is scanned. Then do not auto register. Do
						// it when the pass is registered.
						gei.setMarkAttended(true);
						gei.setUpdatedAt(Calendar.getInstance().getTime());
						try {
							groupEventInviteService.update(gei);
							addSuccess(
									gei.getGroupMember().getFirstName()
											+ " "
											+ gei.getGroupMember()
													.getLastName()
											+ " has now been successfully registered for the event. Amount paid for the event is "
											+ gei.getPaidAmount(), model);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
						}
					}
				}

			}
		}

		model.addAttribute("groupEventInvite", gei);
		model.addAttribute("groupMember", gm);
		return "viewSearchedGroupMember";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/saveScannedGroupMember", method = RequestMethod.POST)
	public String saveScannedGroupMember(Locale locale, Model model,
			@ModelAttribute("groupMember") GroupMember groupMember,
			@RequestParam(required = false) String groupEventInviteId,
			@RequestParam(required = false) String groupEventCode,
			BindingResult results) {
		GroupMember dbGroupMember = new GroupMember();
		if (StringUtils.isNotBlank(groupMember.getSerialNumber())) {
			try {
				dbGroupMember = groupMembersService.findById(groupMember
						.getSerialNumber());

				dbGroupMember.setFirstName(groupMember.getFirstName());
				dbGroupMember.setLastName(groupMember.getLastName());
				// dbGroupMember.setBirthday(groupMember.getBirthday());
				dbGroupMember.setGroupCode(groupMember.getGroupCode());
				dbGroupMember.setMemberCategoryCode(groupMember
						.getMemberCategoryCode());
				dbGroupMember.setPaidMember(groupMember.isPaidMember());
				dbGroupMember.setMembershipStartDate(groupMember
						.getMembershipStartDate());
				dbGroupMember.setMembershipEndDate(groupMember
						.getMembershipEndDate());
				dbGroupMember.setAdultCount(groupMember.getAdultCount());
				dbGroupMember.setKidCount(groupMember.getKidCount());
				dbGroupMember.setPaidMembershipAmount(groupMember
						.getPaidMembershipAmount());
				dbGroupMember.setUpdatedAt(Calendar.getInstance().getTime());
				dbGroupMember.setPrimaryEmail(groupMember.getPrimaryEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error("Could not find the member for serial Number:"
						+ groupMember.getSerialNumber());
			}
		} else {
			dbGroupMember = groupMember;
			dbGroupMember.setSerialNumber(null);
			try {
				dbGroupMember = groupMembersService.insert(dbGroupMember);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				addError("Adding Group Member Failed", model);
				// e.printStackTrace();
			}
			dbGroupMember
					.setMembershipIdentifier(createMembershipIdentifier(dbGroupMember));
			dbGroupMember.setExternalMembershipIdentifier(dbGroupMember
					.getSerialNumber());
		}
		if (results.hasErrors()) {
			model.addAttribute("groupMember", dbGroupMember);
			return "viewSearchedGroupMember";
		}
		try {
			dbGroupMember.setPrimaryEmailVerified(true);
			dbGroupMember = groupMembersService.insertOrUpdate(dbGroupMember);
			addSuccess(
					dbGroupMember.getFirstName() + " "
							+ dbGroupMember.getLastName()
							+ " successfully Added/Updated. Membership ID : "
							+ dbGroupMember.getMembershipIdentifier(), model);
		} catch (Exception e) {
			addAlert("Updating GroupMember Failed", model);
		}

		GroupEvents ges = new GroupEvents();
		if (StringUtils.isNotBlank(groupEventCode)) {
			ges = groupEventsService.findByGroupEventCode(groupEventCode);
			model.addAttribute("groupEvent", ges);
		}
		if (StringUtils.isNotBlank(groupEventInviteId)) {
			try {
				model.addAttribute("groupEventInvite",
						groupEventInviteService.findById(groupEventInviteId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} else if (StringUtils.isNotBlank(groupEventCode)) {

			if (ges != null) {
				try {
					GroupEventInvite geIs = groupEventInviteService
							.findByGroupMemberAndGroupEvent(dbGroupMember, ges);
					if (geIs == null) {

						GroupEventInvite groupEventInvite = new GroupEventInvite();
						// groupEventInvite.setMemberCategoryCode(memberCategoryCode);
						groupEventInvite.setGroupEventCode(ges.getEventCode());
						int eventCodeLength = 6;
						if (ges != null) {
							if (ges.getGroupEventInviteCodeLength() > 0) {
								eventCodeLength = ges
										.getGroupEventInviteCodeLength();
							}
						}

						try {
							groupEventInvite.setGroupMember(dbGroupMember);
							groupEventInvite
									.setMemberCategoryCode(dbGroupMember
											.getMemberCategoryCode());
							groupEventInvite.setGroupCode(dbGroupMember
									.getGroupCode());
							groupEventInvite = groupEventInviteService
									.insert(groupEventInvite);
							if (ges != null
									&& ges.getGroupEventInviteCodeLength() > 0) {
								groupEventInvite
										.setGroupEventInviteCode(groupEventInvite
												.getGroupEventInviteId()
												.substring(0, eventCodeLength)
												.toUpperCase());
							}
							groupEventInvite = groupEventInviteService
									.update(groupEventInvite);
							model.addAttribute("groupEventInvite",
									groupEventInvite);

						} catch (Exception e) {
							e.printStackTrace();
							addError(
									"An error seems to have occured while creating invites. Please contact you system administrator.",
									model);

						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("groupMember", dbGroupMember);
		return "viewSearchedGroupMember";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/json/viewAllGroupMember/{memberCategoryCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupMember> viewAllgroupMemberJson(
			Locale locale, Model model,
			@PathVariable("groupCode") String groupCode,
			@PathVariable String memberCategoryCode) {

		logger.info("GroupCode in json:" + groupCode);
		String catCode = (memberCategoryCode == null
				|| memberCategoryCode.trim().length() <= 0 || "ALL"
				.equalsIgnoreCase(memberCategoryCode)) ? ""
				: memberCategoryCode;
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		if (catCode.trim().length() > 0)
			groupMembers = groupMembersService
					.findByGroupCodeAndMemberCategoryCode(groupCode, catCode);
		else
			groupMembers = groupMembersService.findByGroupCode(groupCode);
		return groupMembers;
	}

	@RequestMapping(value = "/json/viewUnInvitedGroupMembers/{groupEventCode}/{memberCategoryCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupMember> viewUnInvitedGroupMembersByGroupEventCodeAndMemberCategoryCodeJson(
			Locale locale, Model model,
			@PathVariable("groupCode") String groupCode,
			@PathVariable String groupEventCode,
			@PathVariable String memberCategoryCode) {

		String catCode = (memberCategoryCode == null
				|| memberCategoryCode.trim().length() <= 0 || "ALL"
				.equalsIgnoreCase(memberCategoryCode)) ? ""
				: memberCategoryCode;
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		if (catCode.trim().length() > 0)
			groupMembers = groupMembersService
					.findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(
							groupCode, catCode, groupEventCode);
		else
			groupMembers = groupMembersService
					.findByGroupCodeNotExitingInGroupEventInvite(groupCode,
							groupEventCode);
		return groupMembers;
	}

	@RequestMapping(value = "/json/viewUnInvitedGroupMembers/{groupEventCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupMember> viewUnInvitedGroupMembersByGroupEventCodeJson(
			Locale locale, Model model,
			@PathVariable("groupCode") String groupCode,
			@PathVariable String groupEventCode) {
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		groupMembers = groupMembersService
				.findByGroupCodeNotExitingInGroupEventInvite(groupCode,
						groupEventCode);
		return groupMembers;
	}

	@RequestMapping(value = "/saveGroupMember", method = RequestMethod.POST)
	public String saveGroupMember(Locale locale, Model model,
			@ModelAttribute("groupMember") GroupMember groupMember,
			BindingResult results) {
		GroupMember addedGroupMember = new GroupMember();
		if (results.hasErrors()) {
			return "addGroupMember";
		}
		try {
			groupMember.setPrimaryEmailVerified(true);
			addedGroupMember = groupMembersService.insert(groupMember);
			addedGroupMember
					.setMembershipIdentifier(createMembershipIdentifier(addedGroupMember));
			addedGroupMember.setExternalMembershipIdentifier(addedGroupMember
					.getSerialNumber());
			addedGroupMember = groupMembersService.update(addedGroupMember);
			addSuccess(
					addedGroupMember.getFirstName() + " "
							+ addedGroupMember.getLastName()
							+ " successfully added. Membership ID : "
							+ addedGroupMember.getMembershipIdentifier(), model);
			model.addAttribute("groupMember", new GroupMember());
		} catch (Exception e) {
			addAlert("Adding GroupMember Failed", model);
		}

		return "addGroupMember";

	}

	@RequestMapping(value = "/updateGroupMember", method = RequestMethod.POST)
	public @ResponseBody String updateGroupMember(Locale locale, Model model,
			@ModelAttribute("groupMember") GroupMember groupMember,
			BindingResult results) throws Exception {
		GroupMember gm = groupMembersService.findById(groupMember
				.getSerialNumber());
		groupMember.setAddress(gm.getAddress());
		groupMember.setCreatedAt(gm.getCreatedAt());
		groupMember.setCreatedBy(gm.getCreatedBy());
		groupMember.setGroupCode(groupMember.getGroupCode());
		groupMember.setGroupDependents(gm.getGroupDependents());
		groupMember.setMembershipIdentifier(gm.getMembershipIdentifier());
		if (results.hasErrors()) {
			return "error";
		}
		try {
			groupMember.setUpdatedAt(Calendar.getInstance().getTime());
			GroupMember addedGroupMember = groupMembersService
					.update(groupMember);
		} catch (Exception e) {
			addAlert("Updating GroupMember Failed", model);
			return e.getMessage();
		}

		return "success";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/json/viewGroupDependents", method = RequestMethod.GET)
	public @ResponseBody List<GroupDependents> getGroupDependentsByGroupMember(
			Locale locale, Model model, @PathVariable String groupCode,
			@RequestParam(required = false) String serialNumber) {

		List<GroupDependents> gD = new ArrayList<GroupDependents>();
		if (StringUtils.isNotBlank(serialNumber)) {
			GroupMember gm = new GroupMember();
			try {
				gm = groupMembersService.findById(serialNumber);
				gD = gm.getGroupDependents();
			} catch (Exception e) {
				e.printStackTrace();
				return gD;
			}
		}
		return gD;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER, Role.ANONYMOUS })
	@RequestMapping(value = "/updateGroupDependents", method = RequestMethod.POST)
	public @ResponseBody String updateGroupDependents(Locale locale,
			Model model,
			@ModelAttribute("groupDependents") GroupDependents groupDependents,
			BindingResult results, @PathVariable String groupCode,
			@RequestParam(required = false) String serialNumber)
			throws Exception {
		if (StringUtils.isNotBlank(groupDependents.getDependentserialNumber())) {
			GroupDependents gd = groupDependentsService
					.findById(groupDependents.getDependentserialNumber());
			groupDependents.setCreatedAt(gd.getCreatedAt());
			groupDependents.setCreatedBy(gd.getCreatedBy());

		} else {
			groupDependents.setDependentserialNumber(null);
		}
		if (StringUtils.isNotBlank(serialNumber)) {
			GroupMember gm = new GroupMember();
			gm.setSerialNumber(serialNumber);
			groupDependents.setGroupMember(gm);
		} else {
			return "No Parent Serial Number Sent in the request. Hence could not create dependent! Refresh and Try again!";
		}
		groupDependents.setGroupCode(groupCode);
		if (results.hasErrors()) {
			return "error";
		}
		try {
			GroupDependents addedGroupDependents = groupDependentsService
					.insertOrUpdate(groupDependents);
		} catch (Exception e) {
			addAlert("Updating groupDependents Failed", model);
			return "error";
		}

		return "success";

	}

	@RequestMapping(value = "/deleteGroupMember", method = RequestMethod.POST)
	public @ResponseBody String deleteGroupMember(Locale locale, Model model,
			@RequestBody GroupMember groupMember, BindingResult results)
			throws Exception {
		GroupMember gm = groupMembersService.findById(groupMember
				.getSerialNumber());
		groupMember.setAddress(gm.getAddress());
		groupMember.setCreatedAt(gm.getCreatedAt());
		groupMember.setCreatedBy(gm.getCreatedBy());
		groupMember.setGroupCode(groupMember.getGroupCode());

		if (results.hasErrors()) {
			return "error";
		}
		try {
			GroupMember addedGroupMember = groupMembersService
					.update(groupMember);
		} catch (Exception e) {
			addAlert("Updating GroupMember Failed", model);
			return "error";
		}

		return "success";

	}

	@RequestMapping(value = "/json/executeGenericQuery", method = RequestMethod.POST)
	public @ResponseBody List<HashMap<String, String>> executeGenericQuery(
			Model model, @RequestParam(required = true) String query) {
		List<HashMap<String, String>> hm = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("key", "count");
		mp.put("value", String.valueOf(groupMembersService.executeGenericQuery(
				query).size()));
		hm.add(mp);
		return hm;
	}

	@RequestMapping(value = "/viewRegisteredInterests", method = RequestMethod.GET)
	public String viewRegisteredInterests(Locale locale, Model model) {
		return "viewRegisteredInterests";
	}

	@RequestMapping(value = "/json/viewRegisteredInterests", method = RequestMethod.GET)
	public @ResponseBody List<RegisterInterest> viewRegisteredInterests(
			Locale locale, Model model,
			@PathVariable("groupCode") String groupCode) {
		List<RegisterInterest> registerInterests = new ArrayList<RegisterInterest>();
		registerInterests = registerInterestService.findByGroupCode(groupCode,
				true);
		return registerInterests;
	}

	@RequestMapping(value = "/json/updateRegisteredInterests", method = RequestMethod.POST)
	public @ResponseBody String updateRegisteredInterests(Locale locale,
			Model model, @PathVariable("groupCode") String groupCode,
			@ModelAttribute RegisterInterest registerInterest) throws Exception {

		if (registerInterest.getId() > 0) {
			RegisterInterest rint = registerInterestService
					.findById(registerInterest.getId());
			rint.setUpdatedAt(Calendar.getInstance().getTime());
			rint.setCompleted(registerInterest.isCompleted());

			registerInterestService.update(rint);
		}
		return "success";
	}

	@RequestMapping(value = "/viewGroupEventFeedback", method = RequestMethod.GET)
	public String viewGroupEventInviteFeedback(Locale locale, Model model) {
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "viewGroupEventFeedback";
	}

	@RequestMapping(value = "/json/viewGroupEventFeedbackList/{groupEventCode}/{memberCategoryCode}", method = RequestMethod.GET)
	public @ResponseBody List<Feedback> viewGroupEventFeedbackList(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String memberCategoryCode,
			@PathVariable String groupEventCode) {
		List<Feedback> feedback = new ArrayList<Feedback>();
		if (!StringUtils.isBlank(memberCategoryCode)) {
			feedback = feedbackService
					.findByMemberCategoryCodeAndGroupEventCode(
							memberCategoryCode, groupEventCode);

		} else {
			feedback = feedbackService.findByGroupEventCode(groupEventCode);

		}
		return feedback;
	}

	@RequestMapping(value = "/json/viewGroupEventFeedbackList/{groupEventCode}", method = RequestMethod.GET)
	public @ResponseBody List<Feedback> viewGroupEventFeedbackList(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventCode) {
		List<Feedback> feedback = new ArrayList<Feedback>();

		feedback = feedbackService.findByGroupEventCode(groupEventCode);

		return feedback;
	}

	@RequestMapping(value = "/viewGroupEmails", method = RequestMethod.GET)
	public String viewGroupEmails(Locale locale, Model model,
			@PathVariable String groupCode) {
		// The below attribute is just added so as to support spring form (model
		// attribute/command parametr)
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "viewGroupEmails";
	}

	@RequestMapping(value = "/viewGroupSMS", method = RequestMethod.GET)
	public String viewGroupSMS(Locale locale, Model model,
			@PathVariable String groupCode) {
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "viewGroupSMS";
	}

	@RequestMapping(value = "/json/viewGroupSMS/{groupEventCode}/{memberCategoryCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupSMS> viewGroupSMS(Locale locale,
			Model model, @PathVariable String groupCode,
			@PathVariable String memberCategoryCode,
			@PathVariable String groupEventCode) {
		if (!StringUtils.isBlank(memberCategoryCode)) {
			return groupSMSService
					.findSMSByMemberCategoryCodeAndGroupEventCode(
							memberCategoryCode, groupEventCode);
		} else {
			return groupSMSService.findSMSByGroupEventCode(groupEventCode);
		}

	}

	@RequestMapping(value = "/json/viewGroupSMS/{groupEventCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupSMS> viewGroupSMSbyGroupEventCode(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventCode) {

		if ("NULL".equalsIgnoreCase(groupEventCode)) {
			return groupSMSService.findUnassignedSMSByGroupCode(groupCode);
		} else {
			return groupSMSService.findSMSByGroupEventCode(groupEventCode);
		}

	}

	@RequestMapping(value = "/json/viewGroupEmails/{groupEventCode}/{memberCategoryCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupEmail> viewGroupEmails(Locale locale,
			Model model, @PathVariable String groupCode,
			@PathVariable String memberCategoryCode,
			@PathVariable String groupEventCode) {
		if (!StringUtils.isBlank(memberCategoryCode)) {
			return groupEmailsService
					.findEmailsByMemberCategoryCodeAndGroupEventCode(
							memberCategoryCode, groupEventCode);
		} else {
			return groupEmailsService
					.findEmailsByGroupEventCode(groupEventCode);
		}

	}

	@RequestMapping(value = "/json/viewGroupEmails/{groupEventCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupEmail> viewGroupEmailsbyGroupEventCode(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventCode) {

		if ("NULL".equalsIgnoreCase(groupEventCode)) {
			return groupEmailsService
					.findUnassignedEmailsByGroupCode(groupCode);
		} else {
			return groupEmailsService
					.findEmailsByGroupEventCode(groupEventCode);
		}

	}

	@RequestMapping(value = "/json/viewGroupEmails", method = RequestMethod.GET)
	public @ResponseBody List<GroupEmail> viewGroupEmailsJson(Locale locale,
			Model model, @PathVariable String groupCode) {

		return groupEmailsService.findEmailsByGroupCode(groupCode);

	}

	private String createMembershipIdentifier(GroupMember gm) {
		if (gm != null && StringUtils.isNotBlank(gm.getSerialNumber())) {
			return gm.getGroupCode() + "-"
					+ gm.getSerialNumber().substring(2, 8).toUpperCase();
		}
		return "";
	}
}
