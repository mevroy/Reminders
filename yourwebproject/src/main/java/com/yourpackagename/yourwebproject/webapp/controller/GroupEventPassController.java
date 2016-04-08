/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN })
public class GroupEventPassController extends BaseWebAppController {

	private @Autowired GroupEventPassesService groupEventPassesService;
	private @Autowired GroupEventInviteService groupEventInvitesService;
	private @Autowired GroupMembersService groupMembersService;
	protected @Autowired Props props;

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/viewGroupEventPasses", method = RequestMethod.GET)
	public String viewGroupEventPasses(Locale locale, Model model) {
		// Added just to support spring form is jsp
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "viewGroupEventPasses";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupEventPassesByGroupEventCode/{groupEventCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPass> viewJsonGroupEventPassesByGroupEventCode(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventCode) {
		List<GroupEventPass> gep = new ArrayList<GroupEventPass>();
		gep = groupEventPassesService.findByGroupCodeAndGroupEventCode(
				groupCode, groupEventCode, true);
		return gep;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupEventPassesBySerialNumberAndGroupEventCode/{serialNumber}/{groupEventCode}", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPass> viewJsonGroupEventPassesByGroupMemberAndGroupEventCode(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String serialNumber,
			@PathVariable String groupEventCode) {
		List<GroupEventPass> gep = new ArrayList<GroupEventPass>();
		GroupMember groupMember;
		try {
			groupMember = groupMembersService.findById(serialNumber);
			gep = groupEventPassesService.findByGroupMemberAndGroupEventCode(
					groupMember, groupEventCode);
		} catch (Exception e) {
			logger.error("Unable to find the Group Member by the Serial Number:"
					+ serialNumber);
		}

		return gep;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/updatePasses", method = RequestMethod.POST)
	public @ResponseBody String updatePasses(Locale locale, Model model,
			@PathVariable String groupCode,
			@ModelAttribute("groupEventPass") GroupEventPass groupEventPass,
			BindingResult results) {

		GroupEventPass gep = new GroupEventPass();
		try {
			gep = groupEventPassesService.findById(groupEventPass.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Barcode ID :" + groupEventPass.getPassBarcode()
					+ " is not found in the system!";
		}

		gep.setUpdatedAt(Calendar.getInstance().getTime());
		gep.setSold(groupEventPass.isSold());
		gep.setNoOfPeopleTagged(groupEventPass.getNoOfPeopleTagged());
		//gep.setPassStartDate(groupEventPass.getPassStartDate());
		//gep.setPassExpiryDate(groupEventPass.getPassExpiryDate());
		gep.setPassPrice(groupEventPass.getPassPrice());
		gep.setPassInvalidated(groupEventPass.isPassInvalidated());
		gep.setSoldBy(groupEventPass.getSoldBy());
		gep.setTableNumber(groupEventPass.getTableNumber());

		try {
			groupEventPassesService.update(gep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error in updating the Event pass";
		}

		return "success";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/deletePassesFromInvites", method = RequestMethod.POST)
	public @ResponseBody String deletePassesFromInvites(Locale locale,
			Model model,
			@ModelAttribute("groupEventPass") GroupEventPass groupEventPass,
			BindingResult results) {

		if (StringUtils.isBlank(groupEventPass.getId())) {
			return "Group Event Pass ID is not found. Hence could not detach from the invite!";
		}
		try {
			GroupEventPass gep = groupEventPassesService
					.findById(groupEventPass.getId());
			if (gep.getTrackingDate() != null && gep.getGroupMember() != null) {
				return "Event Pass already scanned against a member. First Detach it from the registration page and then detach it here.";
			}
			gep.setGroupEventInvite(null);
			// gep.setGroupMember(null);
			gep.setUpdatedAt(Calendar.getInstance().getTime());
			gep.setSold(false);
			gep.setPassInvalidated(true);
			groupEventPassesService.update(gep);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Unable to Detach the pass from the invite";
		}

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/detachPassesFromRegistrations", method = RequestMethod.POST)
	public @ResponseBody String detachPassesFromRegistrations(Locale locale,
			Model model,
			@ModelAttribute("groupEventPass") GroupEventPass groupEventPass,
			BindingResult results) {

		if (StringUtils.isBlank(groupEventPass.getId())) {
			return "Group Event Pass ID is not found. Hence could not detach from the invite!";
		}
		try {
			GroupEventPass gep = groupEventPassesService
					.findById(groupEventPass.getId());
			gep.setTrackingDate(null);
			gep.setGroupMember(null);
			gep.setUpdatedAt(Calendar.getInstance().getTime());
			groupEventPassesService.update(gep);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Unable to Detach the pass from the invite";
		}

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/registerPassesToAttendees", method = RequestMethod.POST)
	public @ResponseBody String registerPassesToAttendees(Locale locale,
			Model model,
			@ModelAttribute("groupEventPass") GroupEventPass groupEventPass,
			@RequestParam(required = true) String serialNumber,
			@RequestParam(required = true) String groupEventInviteId,
			BindingResult results) {

		int timeZoneDifferenceInHours = 0;

		if (StringUtils.isNotBlank(props.timeZoneDifferenceInHours)) {
			timeZoneDifferenceInHours = Integer
					.parseInt(props.timeZoneDifferenceInHours);
		}
		GroupEventPass gep = groupEventPassesService
				.findByPassBarcode(groupEventPass.getPassBarcode());
		if (gep == null) {
			return "Barcode :" + groupEventPass.getPassBarcode()
					+ " is not found in the system!";
		}

		if (gep.isPassInvalidated()) {
			return "Barcode :"
					+ groupEventPass.getPassBarcode()
					+ " has been marked as invalid! May be it was lost or stolen";
		}
		if (gep.getPassStartDate() != null
				&& gep.getPassStartDate().after(
						DateTime.now()//.plusHours(timeZoneDifferenceInHours)
								.toDate())) {
			long diffHours;
			long diffDays;
			long diffMins;
			Date d1 = DateTime.now()//.plusHours(timeZoneDifferenceInHours)
					.toDate();
			Date d2 = gep.getPassStartDate();
			long diff = d2.getTime() - d1.getTime();
			diffHours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS); // diff
																				// /
																				// (24
																				// *
																				// 60
																				// *
																				// 60
																				// *
																				// 1000);
			diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			diffMins = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
			return "Barcode :"
					+ groupEventPass.getPassBarcode()
					+ " is not yet active. It will be activated at "
					+ CommonUtils.printDateInHomeTimeZone(gep.getPassStartDate())
					+ " (i.e in "
					+ (diffDays < 1 ? (diffHours < 1 ? diffMins + " Minutes"
							: diffHours + " Hours") : diffDays + " days") + ")";
		}
		if (gep.getPassExpiryDate() != null
				&& gep.getPassExpiryDate().before(
						DateTime.now()//.plusHours(timeZoneDifferenceInHours)
								.toDate())) {
			return "Barcode :" + groupEventPass.getPassBarcode()
					+ " has expired at " + CommonUtils.printDateInHomeTimeZone(gep.getPassExpiryDate());
		}
		GroupEventInvite gei = new GroupEventInvite();
		GroupMember gm = new GroupMember();
		try {
			gm = groupMembersService.findById(serialNumber);

			// Doing this because if the pass in not tracked, then ignore who
			// bought the ticket and tag it any other user. TODO how to handle
			// when pass is scanned again
			if (gep.getTrackingDate() != null) {
				if (gep.getGroupMember() != null) {
					if (!StringUtils.equals(gep.getGroupMember().getSerialNumber(), serialNumber)) {
						return "Barcode :" + groupEventPass.getPassBarcode()
								+ " is already scanned against another member "
								+ gep.getGroupMember().getFirstName() + " " + gep.getGroupMember().getLastName()
								+ " at " + CommonUtils.printDateInHomeTimeZone(gep.getTrackingDate());
					} else {
						return "Barcode :"
								+ groupEventPass.getPassBarcode()
								+ " has already been scanned against this member at "
								+ CommonUtils.printDateInHomeTimeZone(gep.getTrackingDate());
					}
				}
				else {
					return "Barcode :" + groupEventPass.getPassBarcode()
								+ " is already scanned against an (unknown)member at " + CommonUtils.printDateInHomeTimeZone(gep.getTrackingDate());
				}
			}

			gei = groupEventInvitesService.findById(groupEventInviteId);

			/*
			 * if (gep.getTrackingDate()!=null && gep.getGroupEventInvite() !=
			 * null && !StringUtils.equals(gep.getGroupEventInvite()
			 * .getGroupEventInviteId(), groupEventInviteId)) { return
			 * "Barcode :" + groupEventPass.getPassBarcode() +
			 * " is already scanned against another member " +
			 * gep.getGroupEventInvite().getGroupMember() .getFirstName() + " "
			 * + gep.getGroupEventInvite().getGroupMember()
			 * .getLastName()+" at "+gep.getTrackingDate(); }
			 */
			if (!gei.getGroupEventCode().equalsIgnoreCase(
					gep.getGroupEventCode())) {
				return "Barcode :" + groupEventPass.getPassBarcode()
						+ " is mapped to a different event!";
			}


			if(!gei.isMarkAttended())
			{
				gei.setMarkAttended(true);
				gei.setUpdatedAt(Calendar.getInstance().getTime());
				groupEventInvitesService.update(gei);
			}
			
			if (gep.getGroupEventInvite() == null) {
				gep.setGroupEventInvite(gei);
			}
			gep.setGroupMember(gm);
			gep.setUpdatedAt(Calendar.getInstance().getTime());
			gep.setTrackingDate(Calendar.getInstance().getTime());
			//Could be a scenario where pass is not sold, but somehow people got it. Need to keep track of it as the money will not be accounted for my the team. So a registration will not deem a pass sold. But will allow to be registered against unsold passes.
			//gep.setSold(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Unable to find Member for the passed Serial Number";
		}

		try {
			groupEventPassesService.update(gep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error in updating the Event pass";
		}

		return "success";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/updatePassesToInvites", method = RequestMethod.POST)
	public @ResponseBody String updatePassesToInvites(Locale locale,
			Model model,
			@ModelAttribute("groupEventPass") GroupEventPass groupEventPass,
			@RequestParam(required = true) String groupEventInviteId,
			BindingResult results) {

		GroupEventPass gep = groupEventPassesService
				.findByPassBarcode(groupEventPass.getPassBarcode());
		if (gep == null) {
			return "Barcode :" + groupEventPass.getPassBarcode()
					+ " is not found in the system!";
		}
		GroupEventInvite gei = new GroupEventInvite();
		try {
			gei = groupEventInvitesService.findById(groupEventInviteId);

			if (!StringUtils.equalsIgnoreCase(gep.getGroupEventCode(),
					gei.getGroupEventCode())) {
				return "Barcode :" + groupEventPass.getPassBarcode()
						+ " is not associate to the event";
			}
			if (gep.getGroupEventInvite() != null) {
				if (!StringUtils.equalsIgnoreCase(gep.getGroupEventInvite()
						.getGroupEventInviteId(), groupEventInviteId)) {
					return "This event pass is already associated to "
							+ gep.getGroupEventInvite().getGroupMember()
									.getFirstName()
							+ " "
							+ gep.getGroupEventInvite().getGroupMember()
									.getLastName()
							+ ". Dis-associate the pass from the other user before proceeding!";
				}
			}
		} catch (Exception e1) {
			return "No Event Invite found for ID:" + groupEventInviteId;
		}
		try {
			gep.setGroupEventInvite(gei);
			gep.setUpdatedAt(Calendar.getInstance().getTime());
			gep.setSold(true);
			gep.setPassInvalidated(false);

		} catch (Exception e) {

			return "Unable to find Group Member for the given serial Number";
		}

		try {
			groupEventPassesService.update(gep);
		} catch (Exception e) {

			return "Error in updating the Event pass";
		}

		return "success";

	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupEventPassesByGroupEventInviteId/{groupEventInviteId}", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPass> viewJsonGroupEventPassesByGroupEventInviteId(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventInviteId) {
		List<GroupEventPass> gep = new ArrayList<GroupEventPass>();
		GroupEventInvite gei;
		try {
			gei = groupEventInvitesService.findById(groupEventInviteId);
			gep = groupEventPassesService.findByGroupEventInvite(gei);
			// gep = groupEventPassesService.findByGroupMemberAndGroupEventCode(
			// gei.getGroupMember(), gei.getGroupEventCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gep;
	}

}
