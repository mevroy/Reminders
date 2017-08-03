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
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.PaymentStatus;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassCategoryService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventPaymentTransactionService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.GroupsService;

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
	private @Autowired GroupEventPassCategoryService groupEventPassCategoryService;
	private @Autowired GroupEventPaymentTransactionService groupEventPaymentTransactionService;
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupsService groupsService;
	protected @Autowired Props props;

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/viewGroupEventPasses", method = RequestMethod.GET)
	public String viewGroupEventPasses(Locale locale, Model model) {
		// Added just to support spring form is jsp
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "viewGroupEventPasses";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/viewGroupEventTableAllocations", method = RequestMethod.GET)
	public String viewGroupEventTableAllocations(Locale locale, Model model) {
		// Added just to support spring form is jsp
		model.addAttribute("groupEventInvite", new GroupEventInvite());
		return "viewGroupEventTableAllocations";
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
		// gep.setPassStartDate(groupEventPass.getPassStartDate());
		// gep.setPassExpiryDate(groupEventPass.getPassExpiryDate());
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
			groupEventPassesService.releaseGroupEventPass(gep);
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
				&& gep.getPassStartDate().after(DateTime.now()// .plusHours(timeZoneDifferenceInHours)
						.toDate())) {
			long diffHours;
			long diffDays;
			long diffMins;
			Date d1 = DateTime.now()// .plusHours(timeZoneDifferenceInHours)
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
					+ CommonUtils.printDateInHomeTimeZone(gep
							.getPassStartDate())
					+ " (i.e in "
					+ (diffDays < 1 ? (diffHours < 1 ? diffMins + " Minutes"
							: diffHours + " Hours") : diffDays + " days") + ")";
		}
		if (gep.getPassExpiryDate() != null
				&& gep.getPassExpiryDate().before(DateTime.now()// .plusHours(timeZoneDifferenceInHours)
						.toDate())) {
			return "Barcode :"
					+ groupEventPass.getPassBarcode()
					+ " has expired at "
					+ CommonUtils.printDateInHomeTimeZone(gep
							.getPassExpiryDate());
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
					if (!StringUtils.equals(gep.getGroupMember()
							.getSerialNumber(), serialNumber)) {
						return "Barcode :"
								+ groupEventPass.getPassBarcode()
								+ " is already scanned against another member "
								+ gep.getGroupMember().getFirstName()
								+ " "
								+ gep.getGroupMember().getLastName()
								+ " at "
								+ CommonUtils.printDateInHomeTimeZone(gep
										.getTrackingDate());
					} else {
						return "Barcode :"
								+ groupEventPass.getPassBarcode()
								+ " has already been scanned against this member at "
								+ CommonUtils.printDateInHomeTimeZone(gep
										.getTrackingDate());
					}
				} else {
					return "Barcode :"
							+ groupEventPass.getPassBarcode()
							+ " is already scanned against an (unknown)member at "
							+ CommonUtils.printDateInHomeTimeZone(gep
									.getTrackingDate());
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

			if (!gei.isMarkAttended()) {
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
			// Could be a scenario where pass is not sold, but somehow people
			// got it. Need to keep track of it as the money will not be
			// accounted for my the team. So a registration will not deem a pass
			// sold. But will allow to be registered against unsold passes.
			// gep.setSold(true);

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

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewApprovedGroupEventPassesByGroupEventInviteId/{groupEventInviteId}", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPass> viewJsonApprovedGroupEventPassesByGroupEventInviteId(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventInviteId) {
		List<GroupEventPass> gep = new ArrayList<GroupEventPass>();
		GroupEventInvite gei;
		try {
			gei = groupEventInvitesService.findById(groupEventInviteId);
			gep = groupEventPassesService
					.findApprovedPassesByGroupEventInvite(gei);
			// gep = groupEventPassesService.findByGroupMemberAndGroupEventCode(
			// gei.getGroupMember(), gei.getGroupEventCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gep;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewUnSoldTickets", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPass> viewUnSoldTickets(Model model,
			@PathVariable String groupCode,
			@RequestParam(required = true) String groupEventCode,
			HttpServletRequest request) throws Exception {
		return groupEventPassesService
				.findUnSoldTicketsByGroupCodeAndGroupEventCode(groupCode,
						groupEventCode);
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupEventPassCategories", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPassCategory> viewGroupEventPassCategories(
			Model model,
			@PathVariable String groupCode,
			@RequestParam(required = true) String groupEventCode,
			@RequestParam(required = false) boolean includeNotAvailableForPurchase,
			HttpServletRequest request) throws Exception {
		return groupEventPassCategoryService.findByGroupCodeAndGroupEventCode(
				groupCode, groupEventCode, includeNotAvailableForPurchase);
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupEventPaymentTransactions", method = RequestMethod.GET)
	public @ResponseBody List<GroupEventPaymentTransaction> viewGroupEventPaymentTransactions(
			Model model, @PathVariable String groupCode,
			@RequestParam(required = true) String groupEventInviteId,
			@RequestParam(required = false) boolean includeExpired,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isBlank(groupEventInviteId)) {
			return new ArrayList<GroupEventPaymentTransaction>();
		}
		try {
			GroupEventInvite groupEventInvite = groupEventInvitesService
					.findById(groupEventInviteId);
			return groupEventPaymentTransactionService.findByGroupEventInvite(
					groupEventInvite, includeExpired);
		} catch (Exception e) {
			return new ArrayList<GroupEventPaymentTransaction>();
		}
	}

	
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/handlePassCategory", method = RequestMethod.POST)
	public @ResponseBody String updatePassCategory(Locale locale, Model model,
			@PathVariable String groupCode,
			@ModelAttribute("groupEventPassCategory") GroupEventPassCategory groupEventPassCategory,
			@RequestParam(required = true) String operation, @RequestParam(required = true) String groupEventCode,
			BindingResult results) {

		GroupEventPassCategory gepc = new GroupEventPassCategory();
		
		if(Key.ADD.equalsIgnoreCase(operation))
		{
			groupEventPassCategory.setCreatedBy(this.getloggedInUser().getUserName());
			groupEventPassCategory.setGroupEvent(groupEventsService.findByGroupEventCode(groupEventCode));
			groupEventPassCategory.setMemberOnlyPurchase(StringUtils.trimToNull(groupEventPassCategory.getMemberOnlyPurchase()));
			groupEventPassCategory.setGroup(groupsService.findByGroupCode(groupCode));
			try {
				groupEventPassCategoryService.insert(groupEventPassCategory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return groupEventPassCategory.getPassCategoryNameShort()
						+ " was not added to the system!";
			}
		}
		else
		{
		try {
			gepc = groupEventPassCategoryService.findById(groupEventPassCategory.getGroupEventPassCategoryId());
		} catch (Exception e1) {
			e1.printStackTrace();
			return " ID :" + groupEventPassCategory.getGroupEventPassCategoryId()
					+ " is not found in the system!";
		}

		gepc.setUpdatedAt(Calendar.getInstance().getTime());
		gepc.setUpdatedBy(this.getloggedInUser().getUserName());
		gepc.setDisablePurchase(groupEventPassCategory.isDisablePurchase());
		gepc.setDisplayOrder(groupEventPassCategory.getDisplayOrder());
		gepc.setDisplayPrice(groupEventPassCategory.isDisplayPrice());
		gepc.setMaxPurchasePerInvite(groupEventPassCategory.getMaxPurchasePerInvite());
		gepc.setMemberOnlyPurchase(StringUtils.trimToNull(groupEventPassCategory.getMemberOnlyPurchase()));
		gepc.setNumberOfPasses(groupEventPassCategory.getNumberOfPasses());
		gepc.setPassBarocodeURL(groupEventPassCategory.getPassBarocodeURL());
		gepc.setPassCategoryName(groupEventPassCategory.getPassCategoryName());
		gepc.setPassCategoryNameShort(groupEventPassCategory.getPassCategoryNameShort());
		gepc.setPassImageURL(groupEventPassCategory.getPassImageURL());
		gepc.setPassPrefix(groupEventPassCategory.getPassPrefix());
		gepc.setPassPrice(groupEventPassCategory.getPassPrice());
		gepc.setPassSuffix(groupEventPassCategory.getPassSuffix());
		gepc.setPurchaseExpiryDateTime(groupEventPassCategory.getPurchaseExpiryDateTime());
		gepc.setPurchaseStartDateTime(groupEventPassCategory.getPurchaseStartDateTime());
		gepc.setRandomPassNumbers(groupEventPassCategory.isRandomPassNumbers());

		
		try {
			groupEventPassCategoryService.update(gepc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error in updating the Event pass";
		}
		}


		return "success";

	}
	
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/assignPasses", method = RequestMethod.POST)
	public @ResponseBody String assignPasses(
			Locale locale,
			Model model,
			@ModelAttribute("groupEvent") GroupEvents groupEvent,
			@RequestParam(required = false) String groupEventInviteId,
			@RequestParam(required = false) String groupEventPaymentTransactionId,
			BindingResult results, @PathVariable String groupCode) {
		if (StringUtils.isBlank(groupEventInviteId)) {
			return "Invitation ID is required to assign tickets";
		}
		GroupEventPaymentTransaction gept = new GroupEventPaymentTransaction();
		boolean createNewTransaction = "new".equalsIgnoreCase(groupEventPaymentTransactionId);
		if (StringUtils.isNotBlank(groupEventPaymentTransactionId) && !createNewTransaction) {
			try {
				gept = groupEventPaymentTransactionService
						.findById(groupEventPaymentTransactionId);
			} catch (Exception e) {
				gept = new GroupEventPaymentTransaction();
			}
		}

		HashMap<String, Long> counter = new HashMap<String, Long>();
		boolean notEnoughTickets = false;
		String notEnoughTicketsMessage = "Error! Not Enough Tickets to be assigned.<br/>";

		if (groupEvent != null) {
			try {
				GroupEventInvite gei = groupEventInvitesService
						.findById(groupEventInviteId);
				double total = 0;
				int totalPasses = 0;
				int subtractFromTotalPasses = 0;
				for (GroupEventPassCategory gepc : groupEvent
						.getGroupEventPassCategories()) {
					if (!StringUtils.equals("0",
							gepc.getGroupEventPassCategoryId())) {/*
																 * GroupEventPassCategory
																 * gepcDB =
																 * groupEventPassCategoryService
																 * .
																 * findById(gepc
																 * .
																 * getGroupEventPassCategoryId
																 * ()); List<
																 * GroupEventPass
																 * > geps =
																 * groupEventPassesService
																 * .
																 * findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory
																 * (
																 * gei.getGroupCode
																 * (), gei.
																 * getGroupEventCode
																 * (), gepc);
																 * if(gepc.
																 * getNumberOfPasses
																 * () > 0 &&
																 * (CollectionUtils
																 * .
																 * isEmpty(geps)
																 * ||
																 * geps.size() <
																 * gepc.
																 * getNumberOfPasses
																 * ())) {
																 * notEnoughTickets
																 * = true;
																 * notEnoughTicketsMessage
																 * +=
																 * "<b>"+gepcDB.
																 * getPassCategoryNameShort
																 * ()+
																 * "</b> - Requested : "
																 * +gepc.
																 * getNumberOfPasses
																 * ()+
																 * " ~ Available : "
																 * +geps.size()+
																 * "<br/>"; }
																 * total +=
																 * (gepc.
																 * getNumberOfPasses
																 * () *
																 * gepc.getPassPrice
																 * ());
																 * totalPasses
																 * += gepc.
																 * getNumberOfPasses
																 * ();
																 */

						GroupEventPassCategory gepcDB = groupEventPassCategoryService
								.findById(gepc.getGroupEventPassCategoryId());
						int maxPassesForEvent = gepcDB.getGroupEvent()
								.getMaxNumberOfPasses();

						if (maxPassesForEvent > 0) {
							if (maxPassesForEvent - subtractFromTotalPasses <= 0) {
								gepcDB.getGroupEvent().setMaxNumberOfPasses(1);
							} else {
								gepcDB.getGroupEvent().setMaxNumberOfPasses(
										maxPassesForEvent
												- subtractFromTotalPasses);
							}
						}
						int availablePasses = groupEventPassesService
								.checkAndReturnPassAvailability(gepcDB,
										(int) gepc.getNumberOfPasses());
						/*
						 * List<GroupEventPass> geps = groupEventPassesService .
						 * findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory
						 * ( gei.getGroupCode(), gei.getGroupEventCode(), gepc);
						 */
						if (availablePasses < gepc.getNumberOfPasses()) {
							notEnoughTickets = true;
							notEnoughTicketsMessage += "<b>"
									+ gepcDB.getPassCategoryNameShort()
									+ "</b> - Requested : "
									+ gepc.getNumberOfPasses()
									+ " ~ Available : " + availablePasses
									+ "<br/>";
						} else {
							subtractFromTotalPasses += gepc.getNumberOfPasses();
						}
						total += (gepc.getNumberOfPasses() * gepcDB
								.getPassPrice());
						totalPasses += gepc.getNumberOfPasses();
						// code to check if this is working or not

					}
				}
				if (notEnoughTickets) {
					return notEnoughTicketsMessage;
				}

				if (totalPasses <= 0) {
					return "Error! Looks like you haven't selected any tickets. Please try again!";
				}

				
				gept.setGroupEventInvite(gei);
				
				if(createNewTransaction)
				{
					gept.setTotalNumberOfProducts(totalPasses);
					gept.setTransactionAmount(total);
					gept.setTransactionDateTime(Calendar
							.getInstance().getTime());
					GroupEvents grpEventDB = groupEventsService.findByGroupEventCode(gei.getGroupEventCode());
					if(grpEventDB.getTransactionExpiryInMinutes()>0)
					{
					gept
							.setTransactionExpiryDateTime(new DateTime().plusHours(24)
									.toDate().after(grpEventDB.getEventDate()) ? grpEventDB
									.getEventDate() : new DateTime().plusHours(24)
									.toDate());
					}
					else {
						gept
						.setTransactionExpiryDateTime(grpEventDB
									.getEventDate());
					}
					String userCode = StringUtils.isNotBlank(gei
							.getGroupEventInviteCode()) ? gei.getGroupEventInviteCode()
							: CommonUtils.generateRandomString(8, 8);
					gept.setUserCode(userCode);
					gept.setGroupEventCode(gei
							.getGroupEventCode());
					
					gept.setUpdatedAt(Calendar.getInstance()
							.getTime());
					gept
							.setPaymentStatus(PaymentStatus.AWAITINGPMT);
					try{
					gept = groupEventPaymentTransactionService
									.insert(gept);
					}
					catch(Exception e)
					{
						
					}
				}
				List<GroupEventPass> assignedPasses = groupEventPassesService
						.assignPassesToTransaction(gept,
								groupEvent.getGroupEventPassCategories());

				if (CollectionUtils.isEmpty(assignedPasses)) {
					
					if(createNewTransaction){
					gept.setPaymentStatus(PaymentStatus.CANCELLED);
					gept.setErrorMessage("Not enough tickets available");
					try{
					gept = groupEventPaymentTransactionService.update(gept);
					}
					catch(Exception e)
					{
						
					}
					}
					return "Error! Not enough passes to be assigned";
				}
				for (GroupEventPass assignedP : assignedPasses) {
					if (counter.containsKey(assignedP
							.getGroupEventPassCategory()
							.getPassCategoryNameShort())) {
						long currentCurrent = counter.get(assignedP
								.getGroupEventPassCategory()
								.getPassCategoryNameShort()) + 1;
						counter.put(assignedP.getGroupEventPassCategory()
								.getPassCategoryNameShort(), currentCurrent);

					} else {
						counter.put(assignedP.getGroupEventPassCategory()
								.getPassCategoryNameShort(), 1L);
					}
				}

				String ticketingStatus = "success";
				for (Map.Entry<String, Long> entry : counter.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					ticketingStatus = ticketingStatus + " <li>" + key + " "
							+ value + " Tickets assigned!" + "</li>";
				}
				return ticketingStatus;
			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/*
		 * try { GroupEventInvite gei = groupEventInvitesService
		 * .findById(groupEventInviteId); List<GroupEventPass> finalPassList =
		 * new ArrayList<GroupEventPass>(); List<GroupEventPassCategory> gpcsRaw
		 * = groupEvent .getGroupEventPassCategories(); HashMap<String,
		 * GroupEventPassCategory> rawCategoriesMap = new HashMap<String,
		 * GroupEventPassCategory>(); for (GroupEventPassCategory gcpR :
		 * gpcsRaw) { if (!StringUtils
		 * .equals(gcpR.getGroupEventPassCategoryId(), "0")) { long count =
		 * gcpR.getNumberOfPasses(); if (rawCategoriesMap.containsKey(gcpR
		 * .getGroupEventPassCategoryId())) { gcpR = rawCategoriesMap.get(gcpR
		 * .getGroupEventPassCategoryId());
		 * gcpR.setNumberOfPasses(gcpR.getNumberOfPasses() + count); }
		 * rawCategoriesMap.put(gcpR.getGroupEventPassCategoryId(), gcpR); } }
		 * List<GroupEventPassCategory> gpcs = new
		 * ArrayList<GroupEventPassCategory>();
		 * 
		 * for (Map.Entry<String, GroupEventPassCategory> entry :
		 * rawCategoriesMap .entrySet()) { GroupEventPassCategory value =
		 * entry.getValue(); gpcs.add(value);
		 * 
		 * }
		 * 
		 * if (CollectionUtils.isNotEmpty(gpcs)) { for (GroupEventPassCategory
		 * gpcUser : gpcs) { if (gpcUser != null && !StringUtils.equals(
		 * gpcUser.getGroupEventPassCategoryId(), "0"))
		 * 
		 * { GroupEventPassCategory gpc = groupEventPassCategoryService
		 * .findById(gpcUser.getGroupEventPassCategoryId());
		 * List<GroupEventPass> geps = groupEventPassesService
		 * .findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory
		 * ( groupCode, gei.getGroupEventCode(), gpc); if (geps != null &&
		 * geps.size() >= gpcUser.getNumberOfPasses()) { for (int a = 0; a <
		 * gpcUser.getNumberOfPasses(); a++) {
		 * geps.get(a).setPassPrice(gpc.getPassPrice());
		 * geps.get(a).setGroupEventPassCategory(gpc);
		 * finalPassList.add(geps.get(a));
		 * 
		 * } Long currentCurrent = gpcUser.getNumberOfPasses(); if
		 * (counter.containsKey(gpc .getPassCategoryNameShort())) {
		 * currentCurrent = counter.get(gpc .getPassCategoryNameShort()) +
		 * currentCurrent;
		 * 
		 * } counter.put(gpc.getPassCategoryNameShort(), currentCurrent);
		 * 
		 * } else { notEnoughTickets = true; notEnoughTicketsMessage =
		 * notEnoughTicketsMessage + "<li>" + gpc.getPassCategoryNameShort() +
		 * " - Requested:" + gpcUser.getNumberOfPasses() + ", Available:" +
		 * geps.size() + "</li>";
		 * 
		 * }
		 * 
		 * } } if (notEnoughTickets) { return notEnoughTicketsMessage; } }
		 * 
		 * if (CollectionUtils.isEmpty(finalPassList)) { return
		 * "Error! Looks like you haven't selected any tickets. Please try again!"
		 * ; } for (GroupEventPass gep : finalPassList) { gep.setSold(true);
		 * gep.setUpdatedAt(Calendar.getInstance().getTime());
		 * gep.setPassInvalidated(false);
		 * gep.setSoldBy(this.getloggedInUser().getUserName());
		 * gep.setGroupEventInvite(gei); groupEventPassesService.update(gep); }
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * String ticketingStatus = "success"; for (Map.Entry<String, Long>
		 * entry : counter.entrySet()) { String key = entry.getKey(); Object
		 * value = entry.getValue(); ticketingStatus = ticketingStatus + " <li>"
		 * + key + " " + value + " Tickets assigned!" + "</li>"; }
		 */

		return "There must have been some problem is the assignment";
	}
}
