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

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.exception.auth.UserPermissionException;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.model.entity.GroupContent;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentType;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;
import com.yourpackagename.yourwebproject.model.entity.enums.PaymentStatus;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupEmailActivityService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassCategoryService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventPaymentTransactionService;
import com.yourpackagename.yourwebproject.service.GroupEventPaymentTypeService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.GroupSMSService;
import com.yourpackagename.yourwebproject.service.GroupsService;

/**
 * @author mdsouza
 *
 */
@Controller
@EnableLogging(loggerClass = "GroupEventInviteRSVPsController")
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
		Role.SUPER_USER, Role.USER, Role.ANONYMOUS })
public class GroupEventInviteRSVPsController extends BaseWebAppController {

	@Autowired
	private GroupEventInviteRSVPService groupEventInviteRSVPService;
	@Autowired
	private GroupEventInviteService groupEventInviteService;
	@Autowired
	private GroupEventsService groupEventsService;
	@Autowired
	private GroupEventPaymentTransactionService groupEventPaymentTransactionService;
	@Autowired
	private GroupEmailsService groupEmailsService;
	@Autowired
	private MailSenderUntypedActor mailSenderUntypedActor;
	@Autowired
	private GroupEmailTemplateService groupEmailTemplateService;
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private GroupEmailActivityService groupEmailActivityService;
	@Autowired
	private GroupEventPassesService groupEventPassesService;
	@Autowired
	private GroupEventPassCategoryService groupEventPassCategoryService;
	@Autowired
	private GroupEventPaymentTypeService groupEventPaymentTypeService;
	@Autowired
	private GroupSMSService groupSMSService;
	@Autowired
	private GroupMembersService groupMembersService;

	private Logger log = LoggerFactory
			.getLogger(GroupEventInviteRSVPsController.class);

	@RequestMapping(value = "/completePayment", method = RequestMethod.POST)
	public String completePayment(
			Model model,
			@RequestParam(required = true) String eId,
			@RequestParam(required = false) Long pmtId,
			@ModelAttribute("groupEventPaymentTransaction") GroupEventPaymentTransaction groupEventPaymentTransaction,
			@PathVariable String groupCode) throws Exception {
		String referenceNumber = StringUtils.trim(groupEventPaymentTransaction
				.getUserReferenceNumber());

		Groups group = groupsService.findByGroupCode(groupCode);
		GroupEventPaymentTransaction gepT = groupEventPaymentTransactionService
				.findById(groupEventPaymentTransaction.getTransactionId());
		if((PaymentStatus.AWAITINGPMT.equals(gepT.getPaymentStatus()) && gepT.getTransactionExpiryDateTime().before(
				Calendar.getInstance().getTime())) || PaymentStatus.EXPIRED.equals(gepT.getPaymentStatus()))
		{
			model.addAttribute("popupModal", true);
			model.addAttribute("popupTitle", "Transaction Expired!");
			model.addAttribute(
					"popupMessage",
					"Oops! Your update did not complete as this transaction has expired. Nothing to worry. Just do the same ticket selection as you did earlier and update the payment reference number!");			
			return this.buy(model, gepT.getGroupEventInvite()
					.getGroupEventInviteId(), "", "false", groupCode);
		}
		GroupEvents groupEventDB = groupEventsService.findByGroupEventCode(gepT
				.getGroupEventCode());
		gepT.setUserReferenceNumber(referenceNumber);
		gepT.setUpdatedAt(Calendar.getInstance().getTime());
		gepT.setTransactionDateTime(Calendar.getInstance().getTime());
		gepT.setTransactionExpiryDateTime(new DateTime().plusHours(96).toDate());
		gepT.setPaymentStatus(PaymentStatus.PROCESSINGPMT);
		if (pmtId > 0) {
			GroupEventPaymentType gep = groupEventPaymentTypeService
					.findById(pmtId);
			gepT.setGroupEventPaymentType(gep);
		}
		if (StringUtils.isNotBlank(referenceNumber)) {

			String body = "Payment Rcvd for :" + groupEventDB.getEventName()
					+ "\n";
			String user = (("Name:"
					+ gepT.getGroupEventInvite().getGroupMember()
							.getFirstName() + " " + gepT.getGroupEventInvite()
					.getGroupMember().getLastName()).length() > 25) ? ("Name:"
					+ gepT.getGroupEventInvite().getGroupMember()
							.getFirstName() + " " + gepT.getGroupEventInvite()
					.getGroupMember().getLastName()).substring(0, 25)
					: ("Name:"
							+ gepT.getGroupEventInvite().getGroupMember()
									.getFirstName() + " " + gepT
							.getGroupEventInvite().getGroupMember()
							.getLastName());
			user += "\nRef No:" + referenceNumber + "\n";
			user += "Pmt Amount:" + gepT.getTransactionAmount() + "\n";
			if (group != null
					&& StringUtils.isNotBlank(group.getContactNumber())) {

				for (String phoneumber : CommonUtils.convertStringToList(
						group.getContactNumber(), ",")) {
					GroupSMS groupSMS = new GroupSMS();
					groupSMS.setMobileNumber(phoneumber);
					groupSMS.setBody(body + user);
					groupSMS.setGroupCode(group.getGroupCode());

					try {
						groupSMSService.insert(groupSMS);
					} catch (Exception e) {
						log.error("Error creating SMS to admin");
					}
				}
			}

			if (groupEventDB.isAutoResponseForRSVPAllowed()
					&& StringUtils.isNotBlank(groupEventDB
							.getAutoResponseRSVPTemplate())) {

				List<GroupEventPass> groupPasses = groupEventPassesService
						.findByTransaction(gepT);

				Map<String, Object> modelMap = new HashMap<String, Object>();
				modelMap.put("groupMember", gepT.getGroupEventInvite()
						.getGroupMember());
				modelMap.put("groupEventInvite", groupEventInviteService
						.findById(gepT.getGroupEventInvite()
								.getGroupEventInviteId()));
				modelMap.put("groupEvent", groupEventDB);
				modelMap.put("groupEmailTemplate", groupEmailTemplateService
						.findbyTemplateName(groupEventDB
								.getAutoResponseRSVPTemplate()));
				List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
						.findByGroupEventInvite(groupEventPaymentTransaction
								.getGroupEventInvite());
				if (tempList != null && tempList.size() > 0) {
					modelMap.put("groupEventInviteRSVP", tempList.get(0));
				}
				modelMap.put("groupEventPaymentTransaction", gepT);
				modelMap.put("groupEventPasses", groupPasses);
				GroupEmail groupEmail = new GroupEmail();
				try {
					groupEmail = groupEmailsService.createEmail(groupEmail,
							modelMap);
					gepT.setCorrespondenceDateTime(Calendar.getInstance()
							.getTime());
				} catch (Exception e) {
					log.error("Unable to create Completion email");
				}
			}

			gepT = groupEventPaymentTransactionService.update(gepT);
		}
		model.addAttribute("info", true);
		model.addAttribute(
				"infoMessage",
				"Your reference number <b>"
						+ gepT.getUserReferenceNumber()
						+ "</b> is now updated against your purchase transaction. Please allow 2 -3 business days for the funds to clear. You will receive an email update once the payment is cleared.");
		return this.buy(model, gepT.getGroupEventInvite()
				.getGroupEventInviteId(), "", "true", groupCode);
		// return "redirect:buy?eId="
		// + gepT.getGroupEventInvite().getGroupEventInviteId();

	}

	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String doBuy(Model model, @RequestParam(required = true) String eId,
			@RequestParam(required = false) String transactionId,
			@ModelAttribute("groupEvent") GroupEvents groupEvent,
			@PathVariable String groupCode) throws Exception {
		if (StringUtils.isBlank(eId)) {
			return "Invitation ID is required to assign tickets";
		}
		GroupEventInvite gei = groupEventInviteService.findById(eId);
		GroupEvents grpEventDB = groupEventsService.findByGroupEventCode(gei
				.getGroupEventCode());

		if (groupEvent != null
				&& CollectionUtils.isNotEmpty(groupEvent
						.getGroupEventPassCategories())) {
			double total = 0;
			int totalPasses = 0;
			for (GroupEventPassCategory gepc : groupEvent
					.getGroupEventPassCategories()) {
				total += (gepc.getNumberOfPasses() * gepc.getPassPrice());
				totalPasses += gepc.getNumberOfPasses();
			}
			GroupEventPaymentTransaction groupEventPaymentTransaction = new GroupEventPaymentTransaction();

			groupEventPaymentTransaction.setTotalNumberOfProducts(totalPasses);
			groupEventPaymentTransaction.setTransactionAmount(total);
			groupEventPaymentTransaction.setTransactionDateTime(Calendar
					.getInstance().getTime());
			if(grpEventDB.getTransactionExpiryInMinutes()>0)
			{
			groupEventPaymentTransaction
					.setTransactionExpiryDateTime(new DateTime().plusMinutes(grpEventDB.getTransactionExpiryInMinutes())
							.toDate().after(grpEventDB.getEventDate()) ? grpEventDB
							.getEventDate() : new DateTime().plusMinutes(grpEventDB.getTransactionExpiryInMinutes())
							.toDate());
			}
			else {
				groupEventPaymentTransaction
				.setTransactionExpiryDateTime(grpEventDB
							.getEventDate());
			}
			String userCode = StringUtils.isNotBlank(gei
					.getGroupEventInviteCode()) ? gei.getGroupEventInviteCode()
					: CommonUtils.generateRandomString(8, 8);
			groupEventPaymentTransaction.setUserCode(userCode);
			groupEventPaymentTransaction.setGroupEventCode(gei
					.getGroupEventCode());
			groupEventPaymentTransaction.setGroupEventInvite(gei);
			groupEventPaymentTransaction.setUpdatedAt(Calendar.getInstance()
					.getTime());
			groupEventPaymentTransaction
					.setPaymentStatus(PaymentStatus.AWAITINGPMT);

			if (StringUtils.isNotBlank(transactionId)) {
				GroupEventPaymentTransaction dbTransaction = groupEventPaymentTransactionService
						.findById(transactionId);
				groupEventPaymentTransaction.setTransactionId(transactionId);
				groupEventPaymentTransaction
						.setTransactionDateTime(dbTransaction
								.getTransactionDateTime());
				groupEventPaymentTransaction
						.setTransactionExpiryDateTime(dbTransaction
								.getTransactionExpiryDateTime());
				groupEventPaymentTransaction
						.setCorrespondenceDateTime(dbTransaction
								.getCorrespondenceDateTime());
				groupEventPaymentTransaction.setUserCode(dbTransaction
						.getUserCode());
				groupEventPaymentTransaction
				.setPaymentStatus(dbTransaction.getPaymentStatus());
			}
			try {
				if (StringUtils.isBlank(transactionId)) {
					groupEventPaymentTransaction = groupEventPaymentTransactionService
							.insertOrUpdate(groupEventPaymentTransaction);
					List<GroupEventPass> assignedPasses = groupEventPassesService
							.assignPassesToTransaction(
									groupEventPaymentTransaction,
									groupEvent.getGroupEventPassCategories());
					if (CollectionUtils.isEmpty(assignedPasses)) {
						groupEventPaymentTransaction
								.setPaymentStatus(PaymentStatus.CANCELLED);
						groupEventPaymentTransaction
								.setErrorMessage("Not enough tickets available");
						groupEventPaymentTransaction = groupEventPaymentTransactionService
								.insertOrUpdate(groupEventPaymentTransaction);
						log.error("No Tickets assigned as tickets info is not available for :"
								+ groupEventPaymentTransaction
										.getGroupEventInvite().getGroupMember()
										.getFirstName()
								+ " - MemID:"
								+ groupEventPaymentTransaction
										.getGroupEventInvite().getGroupMember()
										.getMembershipIdentifier());
						Groups grp = groupsService.findByGroupCode(gei
								.getGroupCode());
						model.addAttribute("popupModal", true);
						model.addAttribute("popupTitle", "Not Enough Tickets");

						model.addAttribute(
								"popupMessage",
								"Unfortunately someone just booked tickets online just before you did and we do not have enough passes to be assigned to you at this stage. Please reduce the number or contact us via email @ "
										+ grp.getContactEmail());
						return this.buy(model, gei.getGroupEventInviteId(),
								"Online Booking", "", gei.getGroupCode());
					} else if (groupEventPaymentTransaction
							.getTransactionAmount() <= 0) {
						groupEventPaymentTransaction
								.setPaymentStatus(PaymentStatus.PROCESSINGPMT);
						groupEventPaymentTransaction = groupEventPaymentTransactionService
								.insertOrUpdate(groupEventPaymentTransaction);
						model.addAttribute("popupModal", true);
						model.addAttribute("popupTitle", "Hurray!");
						model.addAttribute(
								"popupMessage",
								"Congratulations <b>"
										+ gei.getGroupMember().getFirstName()
										+ "</b>! You have successfully reserved your products. If these are passes for any MKC events, you will receive your passes via Email/SMS shortly. <div id='clock'></div>");
						return this.buy(model, gei.getGroupEventInviteId(),
								"Online Booking", "", gei.getGroupCode());
					}
					model.addAttribute("info", true);
					model.addAttribute(
							"infoMessage",
							"Excellent News <b>"
									+ gei.getGroupMember().getFirstName()
									+ "</b>! We have now reserved the products, for you, until <b>"
									+ CommonUtils
											.printDateInHomeTimeZone(groupEventPaymentTransaction
													.getTransactionExpiryDateTime())
									+ "</b> after which this transaction expires. If you are purchasing passes for an event, please make sure to complete the payment before the expiry period to avoid disappointment. The total amount for this transaction is : <b>$ "
									+ groupEventPaymentTransaction
											.getTransactionAmount()
									+ "</b>. <div id='clock'></div>");

				} else {

					groupEventPaymentTransaction = groupEventPaymentTransactionService
							.insertOrUpdate(groupEventPaymentTransaction);
					model.addAttribute("info", true);
					model.addAttribute(
							"infoMessage",
							"Welcome back <b>"
									+ gei.getGroupMember().getFirstName()
									+ "</b>! This transaction  will expire at <b>"
									+ CommonUtils
											.printDateInHomeTimeZone(groupEventPaymentTransaction
													.getTransactionExpiryDateTime())
									+ "</b>. If you are purchasing passes for an event, please make sure to complete the payment before the expiry period to avoid disappointment. The total amount for this transaction is : <b>$ "
									+ groupEventPaymentTransaction
											.getTransactionAmount()
									+ "</b>. <div id='clock'></div>");

				}
			} catch (Exception e) {
				log.error("Error while creating a transaction record for user: "
						+ gei.getGroupMember().getFirstName());
			}

			model.addAttribute("groupEvent", grpEventDB);
			model.addAttribute("groupEventPaymentTransaction",
					groupEventPaymentTransaction);
		}
		return "paymentPage";
	}

	@RequestMapping(value = { "/verifyPurchase", "/completePayment","/cancelTransaction" }, method = RequestMethod.GET)
	public String verifyReload(Locale locale, Model model,
			@RequestParam(required = true) String eId,
			@PathVariable String groupCode) throws Exception {
		return "redirect:buy?eId=" + eId;
	}

	@RequestMapping(value = { "/buyRegister" }, method = RequestMethod.GET)
	public String buyFromEvent(Locale locale, Model model,
			@RequestParam(required = true) String eventCode,
			@RequestParam(required = true) String serialNumber,
			@PathVariable String groupCode) throws Exception {
		try {
			if (StringUtils.isNotBlank(serialNumber)
					&& StringUtils.isNotBlank(eventCode)) {
				GroupMember groupMember = groupMembersService
						.findById(serialNumber);
				GroupEvents ge = groupEventsService
						.findByGroupEventCode(eventCode);
				if (ge != null) {
					GroupEventInvite gei = groupEventInviteService
							.findByGroupMemberAndGroupEvent(groupMember, ge);
					if (gei != null) {
						return "redirect:buy?eId="
								+ gei.getGroupEventInviteId();
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception occured while finding member and event. Returning to register interest Page");
		}

		return "redirect:registerInterest?serialumber=" + serialNumber;
	}

	@RequestMapping(value = "/verifyPurchase", method = RequestMethod.POST)
	public String proceedPayment(Locale locale, Model model,
			@ModelAttribute("groupEvent") GroupEvents groupEvent,
			@RequestParam(required = true) String eId, BindingResult results,
			@PathVariable String groupCode) throws Exception {
		model.addAttribute("groupEvent", groupEvent);
		if (StringUtils.isBlank(eId)) {
			return "Invitation ID is required to assign tickets";
		}
		GroupEventInvite gei = groupEventInviteService.findById(eId);
		model.addAttribute("groupMember", gei.getGroupMember());
		GroupEvents groupEventDB = groupEventsService.findByGroupEventCode(gei
				.getGroupEventCode());
		String noPassMsg = "<br/>";
		if (groupEvent != null) {
			double total = 0;
			int totalPasses = 0;
			boolean notEnoughPasses = false;
			int subtractFromTotalPasses = 0;
			for (GroupEventPassCategory gepc : groupEvent
					.getGroupEventPassCategories()) {
				if (gepc.getNumberOfPasses() > 0) {
					GroupEventPassCategory gepcDB = groupEventPassCategoryService
							.findById(gepc.getGroupEventPassCategoryId());
					int maxPassesForEvent = gepcDB.getGroupEvent()
							.getMaxNumberOfPasses();

					if (maxPassesForEvent > 0) {
						if (maxPassesForEvent - subtractFromTotalPasses <= 0) {
							gepcDB.getGroupEvent().setMaxNumberOfPasses(1);
						} else {
							gepcDB.getGroupEvent()
									.setMaxNumberOfPasses(
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
						notEnoughPasses = true;
						noPassMsg += "<b>" + gepcDB.getPassCategoryNameShort()
								+ "</b> - Requested : "
								+ gepc.getNumberOfPasses() + " ~ Available : "
								+ (availablePasses<0?"0":availablePasses) + "<br/>";
					} else {
						subtractFromTotalPasses += gepc.getNumberOfPasses();
					}
					total += (gepc.getNumberOfPasses() * gepc.getPassPrice());
					totalPasses += gepc.getNumberOfPasses();
					// code to check if this is working or not

				}

			}
			if (notEnoughPasses) {
				model.addAttribute("popupModal", true);
				model.addAttribute("popupTitle", "Not Enough Tickets");

				model.addAttribute(
						"popupMessage",
						"Unfortunately we do not have enough tickets to be assigned to you at this stage. "
								+ noPassMsg);
				return this.buy(model, gei.getGroupEventInviteId(), "", "true",
						gei.getGroupCode());

			}
			if (totalPasses <= 0) {
				model.addAttribute("error", true);
				model.addAttribute(
						"errorMessage",
						"Looks like you have not made a selection from the dropdowns below. If the dropdowns are disabled, then you may not be able to make the purchase online. Please contact us via email");
				return this.buy(model, gei.getGroupEventInviteId(), "", "true",
						gei.getGroupCode());
			}
			GroupEventPaymentTransaction gepT = new GroupEventPaymentTransaction();
			gepT.setTotalNumberOfProducts(totalPasses);
			gepT.setTransactionAmount(total);
			gepT.setTransactionDateTime(Calendar.getInstance().getTime());
			if(groupEventDB.getTransactionExpiryInMinutes()>0)
			{
			gepT.setTransactionExpiryDateTime(new DateTime().plusMinutes(groupEventDB.getTransactionExpiryInMinutes())
					.toDate().after(groupEventDB.getEventDate()) ? groupEventDB
					.getEventDate() : new DateTime().plusMinutes(groupEventDB.getTransactionExpiryInMinutes()).toDate());
			}
			else
			{
				gepT
				.setTransactionExpiryDateTime(groupEventDB
							.getEventDate());
			}
			gepT.setGroupEventInvite(gei);
			gepT.setGroupEventCode(gei.getGroupEventCode());
			String userCode = StringUtils.isNotBlank(gei
					.getGroupEventInviteCode()) ? gei.getGroupEventInviteCode()
					: CommonUtils.generateRandomString(8, 8);
			gepT.setUserCode(userCode);
			model.addAttribute("groupEventPaymentTransaction", gepT);
		}
		return "verifyPage";

		/*
		 * HashMap<String, Long> counter = new HashMap<String, Long>(); boolean
		 * notEnoughTickets = false; String notEnoughTicketsMessage =
		 * "Error! Not Enough Tickets to be assigned."; try { GroupEventInvite
		 * gei = groupEventInviteService .findById(groupEventInviteId);
		 * List<GroupEventPass> finalPassList = new ArrayList<GroupEventPass>();
		 * List<GroupEventPassCategory> gpcsRaw = groupEvent
		 * .getGroupEventPassCategories(); HashMap<String,
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
		 * gpcUser.getNumberOfPasses(); a++) { finalPassList.add(geps.get(a));
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
		 * String ticketingStatus = "success";
		 * 
		 * 
		 * return ticketingStatus;
		 */
	}

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public String buy(Model model, @RequestParam(required = true) String eId,
			@RequestParam(required = false) String rsvpMessage,
			@RequestParam(required = false) String suppressPopUps,
			@PathVariable String groupCode) throws Exception {

		if (StringUtils.isBlank(rsvpMessage)) {
			rsvpMessage = "Online Booking";
		}
		model.addAttribute("rsvpMessage", rsvpMessage);
		GroupEventInvite gei;
		try {
			gei = groupEventInviteService.findById(eId);
			if (gei == null) {
				// model.addAttribute("error", true);
				// model.addAttribute("errorMessage",
				// "Sorry! You do not have a valid invite to RSVP for this event.");
				// return "createRSVP";
				throw new UserPermissionException(
						"Sorry! You do not have a valid invite to RSVP for this event.");
			}
			model.addAttribute("groupMember", gei.getGroupMember());
		} catch (Exception ex) {

			model.addAttribute("error", true);
			model.addAttribute("errorMessage",
					"Sorry! You do not have a valid invite to RSVP for this event.");
			return "buy";

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

		/* code to get the non expire transactions */
		List<GroupEventPaymentTransaction> paymentTransactions = groupEventPaymentTransactionService
				.findByGroupEventInvite(gei, true);
		List<GroupEventPaymentTransaction> viewOnlyTransactions = new ArrayList<GroupEventPaymentTransaction>();
		GroupEventPaymentTransaction activeTransaction = new GroupEventPaymentTransaction();
		if (CollectionUtils.isNotEmpty(paymentTransactions)) {
			for (GroupEventPaymentTransaction transaction : paymentTransactions) {
				transaction = this.expireTransaction(transaction, false);
				if (!PaymentStatus.AWAITINGPMT.equals(transaction
						.getPaymentStatus())) {
					viewOnlyTransactions.add(transaction);
				} else {
					activeTransaction = transaction;
				}

			}

		}

		if (activeTransaction != null
				&& StringUtils.isNotBlank(activeTransaction.getTransactionId())) {

			List<GroupEventPass> passesForTransaction = groupEventPassesService
					.findByTransaction(activeTransaction);

			if (CollectionUtils.isNotEmpty(passesForTransaction)) {
				HashMap<String, GroupEventPassCategory> purchasedPassCategories = new HashMap<String, GroupEventPassCategory>();
				for (GroupEventPass groupEventPass : passesForTransaction) {
					if (purchasedPassCategories.containsKey(groupEventPass
							.getGroupEventPassCategory()
							.getGroupEventPassCategoryId())) {

						GroupEventPassCategory gep = purchasedPassCategories
								.get(groupEventPass.getGroupEventPassCategory()
										.getGroupEventPassCategoryId());
						long count = gep.getNumberOfPasses();
						gep.setNumberOfPasses(count + 1);
						purchasedPassCategories.put(groupEventPass
								.getGroupEventPassCategory()
								.getGroupEventPassCategoryId(), gep);
					} else {
						long count = 1;
						GroupEventPassCategory gepc = groupEventPass
								.getGroupEventPassCategory();
						gepc.setNumberOfPasses(count);
						purchasedPassCategories.put(groupEventPass
								.getGroupEventPassCategory()
								.getGroupEventPassCategoryId(), gepc);
					}
				}
				if (purchasedPassCategories != null
						&& !purchasedPassCategories.isEmpty()) {
					List<GroupEventPassCategory> list = new ArrayList<GroupEventPassCategory>();
					for (Map.Entry<String, GroupEventPassCategory> entry : purchasedPassCategories
							.entrySet()) {
						list.add(entry.getValue());

					}

					double total = 0;
					int totalPasses = 0;
					for (GroupEventPassCategory gepcl : list) {
						total += (gepcl.getNumberOfPasses() * gepcl
								.getPassPrice());
						totalPasses += gepcl.getNumberOfPasses();
					}

					activeTransaction.setTotalNumberOfProducts(totalPasses);
					activeTransaction.setTransactionAmount(total);
					grpEvent.setGroupEventPassCategories(list);
					model.addAttribute("cancelButton", true);
					model.addAttribute("groupEventPaymentTransaction",
							activeTransaction);
					model.addAttribute("groupEvent", grpEvent);
					model.addAttribute("popupModal", true);
					model.addAttribute("popupTitle", "Payment Required");
					model.addAttribute(
							"popupMessage",
							"Our records indicate that you have a pending transaction of <b>$"
									+ total
									+ "</b> to be completed for your purchase. Please make a payment before <b>"
									+ CommonUtils
											.printDateInHomeTimeZone(activeTransaction
													.getTransactionExpiryDateTime())
									+ "</b>. Thanks!");
					return "verifyPage";

				}
			} else {
				log.info("There is active transaction but no tickets assigned. So OK to proceed below i reckon.");
			}

		}
		model.addAttribute("paymentTransactions", paymentTransactions);

		List<GroupEventPass> passes = groupEventPassesService
				.findByGroupEventInvite(gei);
		GroupMember gm = gei.getGroupMember();
		String[] memberTypes = new String[2];
		memberTypes[0] = gm.getMemberCategoryCode();
		String memberType = gm.isActiveMember() ? "ACTIVE" : "INACTIVE";
		memberTypes[1] = memberType;
		HashMap<String, Integer> passCategorisation = new HashMap<String, Integer>();
		if (CollectionUtils.isNotEmpty(passes)) {
			for (GroupEventPass pass : passes) {
				if (passCategorisation
						.containsKey(pass.getGroupEventPassCategory()
								.getPassCategoryNameShort())) {
					int count = passCategorisation.get(pass
							.getGroupEventPassCategory()
							.getPassCategoryNameShort()) + 1;
					passCategorisation.put(pass.getGroupEventPassCategory()
							.getPassCategoryNameShort(), count);
				} else {
					passCategorisation.put(pass.getGroupEventPassCategory()
							.getPassCategoryNameShort(), 1);
				}
			}
		}

		List<GroupEventPassCategory> passListForPurchase = groupEventPassCategoryService
				.findByGroupCodeAndGroupEventCodeForMemberType(groupCode,
						gei.getGroupEventCode(), false, true, memberTypes);
		if (passCategorisation != null) {
			for (GroupEventPassCategory gepc : passListForPurchase) {
				if (passCategorisation.containsKey(gepc
						.getPassCategoryNameShort())) {
					int count = passCategorisation.get(gepc
							.getPassCategoryNameShort());
					int maxPurchasePerInvite = gepc.getMaxPurchasePerInvite();
					if(maxPurchasePerInvite >=0){
					int remainingBuyingPower = maxPurchasePerInvite - count;
					gepc.setMaxPurchasePerInvite(remainingBuyingPower > 0 ? remainingBuyingPower
							: 0);}
				}

				if (gepc.getNumberOfPasses() > 0) {
					long soldPassesForGEC = groupEventPassesService
							.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
									groupCode, gei.getGroupEventCode(), gepc)
							.size();
					int remainingEventCapacity = (int) gepc.getNumberOfPasses()
							- (int) soldPassesForGEC;
					if (remainingEventCapacity <= 0) {
						// Here disable purchase is true because event is maxed
						// out.
						gepc.setDisablePurchase(true);
					}
				}
			}
		}
		grpEvent.setGroupEventPassCategories(passListForPurchase);
		model.addAttribute("passListForPurchase", passListForPurchase);

		/*
		 * List<GroupEventInviteRSVP> rsvpList = groupEventInviteRSVPService
		 * .findByGroupEventInvite(gei);
		 * 
		 * GroupEventInviteRSVP ger = new GroupEventInviteRSVP();
		 * 
		 * if (rsvpList != null && rsvpList.size() > 0) { ger = rsvpList.get(0);
		 * model.addAttribute("info", true); model.addAttribute( "infoMessage",
		 * "Our records indicate that you have already RSVP'd. " +
		 * (!grpEvent.isPaidEvent() ? "No further action needed. " : "") +
		 * (grpEvent.getRsvpDeadlineDate() != null &&
		 * grpEvent.getRsvpDeadlineDate().before(
		 * Calendar.getInstance().getTime()) ?
		 * "No further changes are allowed as the RSVP deadline date has crossed. "
		 * : "")); } else { if (grpEvent.isPaidEvent()) {
		 * model.addAttribute("info", true); model.addAttribute( "infoMessage",
		 * "<b>How does it work?</b><br/><br/>Just provide us the count for the number of people attending in the below form and click SUBMIT. You will receive an email within a minute, detailing the total amount that needs to be paid along with payment instructions. Just follow the instructions in that email and you will receive your E-Tickets soon!"
		 * ); } }
		 * 
		 * ger.setGroupEventInvite(gei);
		 */
		if (grpEvent.getMaxNumberOfPasses() > 0) {
			// This has to be before RSVP deadline because otherwise, even after
			// the deadline it will show some tickets being available
			List<GroupEventPass> gep = groupEventPassesService
					.findSoldTicketsByGroupCodeAndGroupEventCode(groupCode,
							gei.getGroupEventCode());

			int percentageTicketsSold = ((gep.size() * 100) / grpEvent
					.getMaxNumberOfPasses());
			if (percentageTicketsSold > 50 && percentageTicketsSold < 100) {
				if (StringUtils.isBlank(suppressPopUps)) {
					model.addAttribute("error", true);
					model.addAttribute(
							"errorMessage",
							"Tickets are selling soon. Just "
									+ (100 - percentageTicketsSold)
									+ "% of the tickets are remaining. Act fast to avoid dissapointment.");
				}
			}

			if (gep != null
					&& (grpEvent.getMaxNumberOfPasses() - gep.size() <= 0)) {
				model.addAttribute("disableButton", true);
				if (StringUtils.isBlank(suppressPopUps)) {
					model.addAttribute("popupModal", true);
					model.addAttribute("popupTitle", "Oops!");
					model.addAttribute(
							"popupMessage",
							"Sorry! The tickets for this event are sold out at the moment. Contact us with further questions @  "
									+ grp.getContactEmail());

				}
			}

		}

		if (grpEvent.getRsvpDeadlineDate() != null
				&& grpEvent.getRsvpDeadlineDate().before(
						Calendar.getInstance().getTime())) {
			model.addAttribute("disableButton", true);
			if (StringUtils.isBlank(suppressPopUps)) {
				model.addAttribute("popupModal", true);
				model.addAttribute("popupTitle", "Oops!");
				model.addAttribute(
						"popupMessage",
						"Sorry! The booking deadline for this event has passed. If you still need tickets, please follow the instructions in your invite email or contact your event organiser. Contact us with further questions @ "
								+ grp.getContactEmail());
			}
		}

		/*
		 * if (grpEvent.isPaidEvent() &&
		 * StringUtils.isNotBlank(gei.getTransactionReference()) &&
		 * "true".equalsIgnoreCase(ger.getRsvpOutcome())) {
		 * model.addAttribute("disableButton", true);
		 * model.addAttribute("popupModal", true);
		 * model.addAttribute("popupTitle", "Status"); model.addAttribute(
		 * "popupMessage",
		 * "We have received your Transaction Reference Number - " +
		 * gei.getTransactionReference().toUpperCase() +
		 * " and are processing it. No further actions can be processed on this page at this time. Once your transaction is approved, this page will show the status of your payment (usually within 2 - 3 business days). If you need to purchase additional tickets, contact us with further details @ "
		 * + grp.getContactEmail());
		 * 
		 * }
		 */

		/*
		 * if (grpEvent.isPaidEvent() && gei.isTransactionApproved()) {
		 * model.addAttribute("disableButton", true);
		 * model.addAttribute("popupModal", true);
		 * model.addAttribute("popupTitle", "Status"); model.addAttribute(
		 * "popupMessage",
		 * "Please note that we have received and approved your payment" +
		 * (gei.getPaidAmount() > 0.0 ? " of $" + gei.getPaidAmount() : "") +
		 * (StringUtils.isNotBlank(gei .getTransactionReference()) ?
		 * " for transaction reference number - " +
		 * gei.getTransactionReference() : "") +
		 * ". You should be receiving your tickets soon. No further actions can be processed on this page at this time. If you need to purchase additional tickets, contact us with further details @ "
		 * + grp.getContactEmail());
		 * 
		 * }
		 * 
		 * model.addAttribute("groupEventInviteRSVP", ger);
		 */
		model.addAttribute("groupEvent", grpEvent);
		model.addAttribute("groupEventInvite", gei);
		model.addAttribute("groupEventPaymentTransaction", activeTransaction);
		return "buy";
	}

	@RequestMapping(value = "/createRSVP", method = RequestMethod.GET)
	public String createRSVPRequest(Model model,
			@RequestParam(required = true) String groupEventInviteId,
			@RequestParam(required = false) String rsvpMessage,
			@PathVariable String groupCode) throws Exception {
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
		} else {
			if (grpEvent.isPaidEvent()) {
				model.addAttribute("info", true);
				model.addAttribute(
						"infoMessage",
						"<b>How does it work?</b><br/><br/>Just provide us the count for the number of people attending in the below form and click SUBMIT. You will receive an email within a minute, detailing the total amount that needs to be paid along with payment instructions. Just follow the instructions in that email and you will receive your E-Tickets soon!");
			}
		}

		ger.setGroupEventInvite(gei);
		if (grpEvent.getMaxNumberOfPasses() > 0) {
			// This has to be before RSVP deadline because otherwise, even after
			// the deadline it will show some tickets being available
			List<GroupEventPass> gep = groupEventPassesService
					.findSoldTicketsByGroupCodeAndGroupEventCode(groupCode,
							gei.getGroupEventCode());

			int percentageTicketsSold = ((gep.size() * 100) / grpEvent
					.getMaxNumberOfPasses());
			if (percentageTicketsSold > 50) {
				model.addAttribute("popupModal", true);
				model.addAttribute("popupTitle", "Hurry Up!");
				model.addAttribute(
						"popupMessage",
						"Tickets are selling soon. Just "
								+ (100 - percentageTicketsSold)
								+ "% of the tickets are remaining. Act fast to avoid dissapointment.");
			}

			if (gep != null
					&& (grpEvent.getMaxNumberOfPasses() - gep.size() <= 0)) {
				model.addAttribute("disableButton", true);
				model.addAttribute("popupModal", true);
				model.addAttribute("popupTitle", "Oops!");
				model.addAttribute(
						"popupMessage",
						"Sorry! The tickets for this event are sold out at the moment. Contact us with further questions @ "
								+ grp.getContactEmail());

			}

		}

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
				GroupEmailActivity groupEmailActivity = new GroupEmailActivity();
				groupEmailActivity.setEmailActivity(EmailActivity.CREATE);
				groupEmailActivity.setActivityTime(groupEmail.getCreatedAt());
				groupEmailActivity.setActivityBy(groupMember.getFirstName()
						+ " " + groupMember.getLastName());
				groupEmailActivity.setGroupEmail(newEmail);
				groupEmailActivityService.insert(groupEmailActivity);
				modelMap.put("groupEmail", newEmail);
				newEmail.setBody(mailSenderUntypedActor.prepareEmailBody(
						gEmailTemplate.getTemplateName(), modelMap));
				newEmail.setEmailHeld(groupEventInvite.isInviteHeld()
						|| !groupMember.isPrimaryEmailVerified());
				groupEmailsService.insertOrUpdate(newEmail);
				GroupEmailActivity groupEmailActivity2 = new GroupEmailActivity();
				groupEmailActivity2.setEmailActivity(EmailActivity.UPDATE);
				groupEmailActivity2.setActivityTime(Calendar.getInstance()
						.getTime());
				groupEmailActivity2.setActivityBy(groupMember.getFirstName()
						+ " " + groupMember.getLastName());
				groupEmailActivity2.setGroupEmail(newEmail);
				groupEmailActivityService.insert(groupEmailActivity2);
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

	@RequestMapping(value = "/loadCustomInvitation", method = RequestMethod.GET)
	public String loadCustomInvitationContent(Model model,
			@RequestParam(required = false) String groupEventInviteId,
			@PathVariable String groupCode) throws Exception {
		if (StringUtils.isNotBlank(groupEventInviteId)) {
			try {
				GroupEventInvite gei = groupEventInviteService
						.findById(groupEventInviteId);
				model.addAttribute("groupEventInvite", gei);
				List<GroupEventInvite> geis = new ArrayList<GroupEventInvite>();
				List<GroupEventInviteRSVP> grsvps = new ArrayList<GroupEventInviteRSVP>();
				geis = groupEventInviteService.findByGroupCodeAndEventCode(
						gei.getGroupCode(), gei.getGroupEventCode());

				for (GroupEventInvite geI : geis) {
					List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
							.findByGroupEventInvite(geI);
					if (tempList != null && tempList.size() > 0) {
						if (StringUtils.isNotBlank(tempList.get(0)
								.getRsvpComments()))
							grsvps.add(tempList.get(0));
					}
				}
				model.addAttribute("groupEventInviteRSVPs", grsvps);

			} catch (Exception nf) {
				log.error("Unable to Invite for Invite ID:"
						+ groupEventInviteId);
				model.addAttribute("exception", "In-valid Invite");
				return "error";
			}
		}
		return "loadCustomInvite";

	}

	public GroupEventPaymentTransaction expireTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction,
			boolean ignoreReferenceNumber)

	{
		if (groupEventPaymentTransaction == null
				|| PaymentStatus.EXPIRED.equals(groupEventPaymentTransaction
						.getPaymentStatus())) {
			log.error("Transaction is null or Already Marked Expired.");
		} else {
			if (!groupEventPaymentTransaction.isTransactionApproved()
					&& ((groupEventPaymentTransaction
							.getTransactionExpiryDateTime() != null
							&& groupEventPaymentTransaction
									.getTransactionExpiryDateTime().before(
											Calendar.getInstance().getTime()) && (PaymentStatus.AWAITINGPMT
								.equals(groupEventPaymentTransaction
										.getPaymentStatus()))))) {
				if (ignoreReferenceNumber
						|| StringUtils.isBlank(groupEventPaymentTransaction
								.getUserReferenceNumber())) {
					groupEventPaymentTransaction
							.setPaymentStatus(PaymentStatus.EXPIRED);
					groupEventPaymentTransaction.setUpdatedAt(Calendar
							.getInstance().getTime());

					List<GroupEventPass> groupPasses = groupEventPassesService
							.findByTransaction(groupEventPaymentTransaction);
					if (CollectionUtils.isNotEmpty(groupPasses)) {
						for (GroupEventPass pass : groupPasses) {
							groupEventPassesService.releaseGroupEventPass(pass);
						}
					}
					try {
						groupEventPaymentTransaction = groupEventPaymentTransactionService
								.update(groupEventPaymentTransaction);
					} catch (Exception e) {
						log.error("Error - Expiring the transaction failed");
					}
				}
			}
		}

		return groupEventPaymentTransaction;
	}

	@RequestMapping(value = "/cancelTransaction", method = RequestMethod.POST)
	public String cancelTransaction(Model model,
			@RequestParam(required = false) String transactionId,
			@RequestParam(required = true) String eId,
			@PathVariable String groupCode, boolean ignoreReferenceNumber)
			throws Exception

	{
		if (StringUtils.isBlank(transactionId)) {
			String returnPage = this.buy(model, eId, "", "", groupCode);
			return returnPage;
		}
		GroupEventPaymentTransaction groupEventPaymentTransaction;
		try {
			groupEventPaymentTransaction = groupEventPaymentTransactionService
					.findById(transactionId);

			if (groupEventPaymentTransaction == null
					|| PaymentStatus.CANCELLED
							.equals(groupEventPaymentTransaction
									.getPaymentStatus())) {
				log.error("Transaction is null or Already Marked Expired.");

				String nextP = this.buy(model, eId, "", "", groupCode);
				addError(
						"Your transaction is already marked as cancelled. Refresh this page view the status of your most recent transactions",
						model);
				return nextP;

			} else {
				if (PaymentStatus.APPROVED.equals(groupEventPaymentTransaction
						.getPaymentStatus())
						|| PaymentStatus.PROCESSED
								.equals(groupEventPaymentTransaction
										.getPaymentStatus())) {
					String nextP = this.buy(model, eId, "", "", groupCode);
					addError(
							"Your transaction is already Approved/Processed. You cannot cancel a Approved/Processed transaction! Refresh this page view the status of your most recent transactions",
							model);
					return nextP;

				} else if (ignoreReferenceNumber
						|| StringUtils.isBlank(groupEventPaymentTransaction
								.getUserReferenceNumber())) {
					groupEventPaymentTransaction
							.setPaymentStatus(PaymentStatus.CANCELLED);
					groupEventPaymentTransaction.setUpdatedAt(Calendar
							.getInstance().getTime());

					List<GroupEventPass> groupPasses = groupEventPassesService
							.findByTransaction(groupEventPaymentTransaction);
					if (CollectionUtils.isNotEmpty(groupPasses)) {
						for (GroupEventPass pass : groupPasses) {
							groupEventPassesService.releaseGroupEventPass(pass);
						}
					}
					try {
						groupEventPaymentTransaction = groupEventPaymentTransactionService
								.update(groupEventPaymentTransaction);
					} catch (Exception e) {
						log.error("Error - Expiring the transaction failed");
					}
				}
				String nextPage = this.buy(model, eId, "", "", groupCode);
				addSuccess("Your transaction was successfully cancelled!",
						model);
				return nextPage;
			}
		} catch (Exception e1) {
			String page = this.buy(model, eId, "", "", groupCode);
			addError(
					"Error occured while updating your trasaction. If this problem persists, please contact your administrator!",
					model);
			return page;
		}

	}

}
