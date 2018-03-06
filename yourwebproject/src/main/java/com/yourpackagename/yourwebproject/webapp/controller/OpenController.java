/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.admin.service.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.controller.BaseController;
import com.yourpackagename.framework.exception.database.NotFoundException;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.ExternalScanner;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.request.ICODYScannerRO;
import com.yourpackagename.yourwebproject.service.ExternalScannerService;
import com.yourpackagename.yourwebproject.service.GroupContentService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupInterestService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.GroupsService;
import com.yourpackagename.yourwebproject.service.LocalFileService;
import com.yourpackagename.yourwebproject.service.LoggerService;
import com.yourpackagename.yourwebproject.webapp.common.View;

/**
 * @author mevan.d.souza A controller to allow few old link fixing stuff and
 *         logic for redirects for already send emails.
 *
 */
@Controller
@EnableLogging(loggerClass = "OpenController")
public class OpenController extends BaseController {
	private @Autowired GroupInterestService groupInterestService;
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
	private GroupMembersService groupMembersService;
	@Autowired
	private GroupEventPassesService groupEventPassesService;
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private LocalFileService fileService;
	@Autowired
	private ExternalScannerService externalScannerService;

	private @Autowired GroupContentService groupContentService;
	private @Autowired LoggerService loggerService;

	@Autowired
	private Props props;

	private Logger log = LoggerFactory.getLogger(OpenController.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request,
			HttpServletResponse response, Exception ex, Locale locale) {
		ModelAndView mav = new ModelAndView(View.error);
		StringBuilder stack = new StringBuilder();

		log.error("[baseWebAppExceptionHandler] Response: " + ex.getMessage());
		mav.addObject("exception", ex.getMessage());
		for (StackTraceElement element : ex.getStackTrace()) {
			stack.append(element.toString());
			stack.append("\n");
		}

		mav.addObject("stack", stack.toString());
		String errorTrace = ex.getMessage() + stack.toString();
		AuditLog alog = new AuditLog(request);
		alog.setClazz("ExceptionHandler");
		alog.setError(true);
		alog.setErrorTrace(StringUtils.isNotBlank(errorTrace)
				&& errorTrace.trim().length() >= 250 ? errorTrace.substring(0,
				249) : errorTrace);
		try {
			loggerService.insertOrUpdate(alog);
		} catch (Exception exc) {
			log.error("Unable to create entry into Audit Log");
		}

		return mav;
	}

	@RequestMapping(value = "/registerInterest", method = { RequestMethod.GET,
			RequestMethod.HEAD })
	public String registerInterest(Model model,
			@RequestParam(required = false) String groupCode,
			@RequestParam(required = false) String groupEventCode,
			@RequestParam(required = false) String serialNumber,
			@RequestParam(required = false) String interestType,
			@RequestParam(required = false) String campaign)
			throws NotFoundException {

		if (StringUtils.isNotBlank(groupCode)) {
			return Key.redirect
					+ "/"
					+ groupCode
					+ "/registerInterest"
					+ (StringUtils.isNotBlank(interestType) ? "?interestType="
							+ interestType : "?interestType")
					+ (StringUtils.isNotBlank(serialNumber) ? "&serialNumber="
							+ serialNumber : "&serialNumber")
					+ (StringUtils.isNotBlank(campaign) ? "&campaign="
							+ campaign : "&campaign");
		}

		model.addAttribute("exception", "Sorry! This page cannot be found.");
		return "error";

	}

	@RequestMapping(value = { "/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch" }, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		return "redirect:/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch/";
	}

	// Logic Simply added to redirect URL path to request Param as Submit form
	// doesn't work too well with path param. If you need to redirect to group
	// context then append "/{groupCode" to the redirect.
	@RequestMapping(value = "/createRSVP/{groupEventInviteId}",method = RequestMethod.GET)
	public String createRSVP(Model model,
			@PathVariable String groupEventInviteId,
			@RequestParam(required = false) String rsvpMessage)
			throws Exception {
		GroupEventInvite gei;
		try {
			gei = groupEventInviteService.findById(groupEventInviteId);
			return Key.redirect
					+ "/"
					+ gei.getGroupCode()
					+ "/createRSVP?groupEventInviteId="
					+ groupEventInviteId
					+ (StringUtils.isNotBlank(rsvpMessage) ? "&rsvpMessage="
							+ rsvpMessage : "");
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

	}

	
	@RequestMapping(value = "/buy/{groupEventInviteIdOrEventCode}",method = RequestMethod.GET)
	public String buy(Model model,
			@PathVariable String groupEventInviteIdOrEventCode,
			@RequestParam(required = false) String rsvpMessage)
			throws Exception {

		try {
			GroupEventInvite gei = groupEventInviteService
					.findByGroupEventInviteCode(groupEventInviteIdOrEventCode);
			if (gei == null) {
				gei = groupEventInviteService.findById(groupEventInviteIdOrEventCode);
			}
			return Key.redirect
					+ "/"
					+ gei.getGroupCode()
					+ "/buy?eId="
					+ gei.getGroupEventInviteId()
					+ (StringUtils.isNotBlank(rsvpMessage) ? "&rsvpMessage="
							+ rsvpMessage : "");
		} catch (Exception ex) {

			model.addAttribute("error", true);
			model.addAttribute("errorMessage",
					"Sorry! You do not have a valid invite for this event.");
			return "error";

			/*
			 * throw new UserPermissionException(
			 * "Sorry! You do not have a valid invite to RSVP for this event.");
			 */
		}

	}


	
	@RequestMapping(value = "/cInvite/{groupEventInviteIdOrEventCode}",method = RequestMethod.GET)
	public String customInvite(Model model,
			@PathVariable String groupEventInviteIdOrEventCode)
			throws Exception {

		try {
			GroupEventInvite gei = groupEventInviteService
					.findByGroupEventInviteCode(groupEventInviteIdOrEventCode);
			if (gei == null) {
				gei = groupEventInviteService.findById(groupEventInviteIdOrEventCode);
			}
			return Key.redirect
					+ "/"
					+ gei.getGroupCode()
					+ "/loadCustomInvitation?groupEventInviteId="
					+ gei.getGroupEventInviteId()
					+"&serialNumber="+gei.getGroupMember().getSerialNumber()
					;
		} catch (Exception ex) {

			model.addAttribute("error", true);
			model.addAttribute("errorMessage",
					"Sorry! Please make sure the link you clicked is complete.");
			return "error";

			/*
			 * throw new UserPermissionException(
			 * "Sorry! You do not have a valid invite to RSVP for this event.");
			 */
		}

	}
	
	
	@RequestMapping(value = "/pass/{groupEventInviteIdOrEventCode}",method = RequestMethod.GET)
	public String passPDF(Model model,
			@PathVariable String groupEventInviteIdOrEventCode
			)
			throws Exception {

		try {
			GroupEventInvite gei = groupEventInviteService
					.findByGroupEventInviteCode(groupEventInviteIdOrEventCode);
			if (gei == null) {
				gei = groupEventInviteService.findById(groupEventInviteIdOrEventCode);
			}
			GroupEvents ge = groupEventsService.findByGroupEventCode(gei.getGroupEventCode());
			if(ge!=null && !CommonUtils.isValidDates(null, ge.getExpiryDate()))
			{
				model.addAttribute("exception",
						"Sorry! This event has already occured in the past and you will no longer be able to download the passes.");
				return "error";
			}
			List<GroupEventPass> gep = groupEventPassesService
					.findApprovedPassesByGroupEventInvite(gei);
			if(CollectionUtils.isEmpty(gep))
			{
				model.addAttribute("exception",
						"Sorry! You do not have passes to download for this event.");
				return "error";	
			}
			return Key.redirect
					+ "/"
					+ gei.getGroupCode()
					+ "/generatePasses?groupEventInviteIdOrEventCode="
					+ groupEventInviteIdOrEventCode
					;
		} catch (Exception ex) {

			
			model.addAttribute("exception",
					"Sorry! An error has occured. Make sure you copy paste the full URL that you received in the email to download your tickets.");
			return "error";

			/*
			 * throw new UserPermissionException(
			 * "Sorry! You do not have a valid invite to RSVP for this event.");
			 */
		}

	}
	
	
	@RequestMapping(value = "/groupEventFeedback/{groupEventInviteId}", method = RequestMethod.GET)
	public String groupEventFeedback(Model model,
			@PathVariable String groupEventInviteId,
			@RequestParam(required = false) String redirectURL)
			throws Exception {
		GroupEventInvite gei = null;
		if (StringUtils.isNotBlank(redirectURL)) {
			return "redirect:" + redirectURL;
		}
		try {
			gei = groupEventInviteService.findById(groupEventInviteId);
			return Key.redirect + "/" + gei.getGroupCode()
					+ "/groupEventFeedback?groupEventInviteId="
					+ groupEventInviteId;
		} catch (Exception e) {
			logger.error("Unable to find event invite for id:"
					+ groupEventInviteId);
			model.addAttribute("exception",
					"Sorry! The invite seems to be invalid");
			return "error";

		}

	}

	@RequestMapping(value = "/viewEmail/{groupEmailId}", method = RequestMethod.GET)
	public @ResponseBody String viewEmail(Model model,
			@PathVariable String groupEmailId, HttpServletRequest request)
			throws Exception {
		GroupEmail groupEmail = groupEmailsService.findById(groupEmailId);
		if (groupEmail != null && groupEmail.getEmailViewedDate() == null) {
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
		return request.getRequestURL().toString()
				.replaceAll(request.getRequestURI(), request.getContextPath())
				+ "/res/custom/images/email/generic/facebook.png";
	}

	@RequestMapping(value = "/loadEmailWebversion/{groupEmailId}", method = RequestMethod.GET)
	public String loadEmailWebversion(Model model,
			@PathVariable String groupEmailId, HttpServletRequest request)
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
		return "loadEmailWebversion";
		}
		}
		catch(Exception e)
		{
			model.addAttribute("exception", "Oops! Looks like you are trying to dig into an old email. This email has been archived! Sorry about that.");
		}
		return "error";
	}

	@RequestMapping(value = "/loadEmailTemplate/{templateName}", method = RequestMethod.GET)
	public String loadEmailTemplate(Model model,
			@PathVariable String templateName, HttpServletRequest request)
			throws Exception {
		GroupEmailTemplate groupEmailT = groupEmailTemplateService
				.findbyTemplateName(templateName);
		if (groupEmailT != null) {
			model.addAttribute("emailHTML", groupEmailT.getTemplateContent());
		}
		return "loadEmailWebversion";
	}

	/*
	 * @RequestMapping(value = "/viewEmail/{groupEmailId}/{groupEventInviteId}")
	 * public @ResponseBody String viewEmail(Model model, @PathVariable String
	 * groupEventInviteId, @PathVariable String groupEmailId, HttpServletRequest
	 * request) throws Exception { GroupEventInvite gei =
	 * groupEventInviteService.findById(groupEventInviteId); if(gei!=null &&
	 * !gei.isInviteDelivered()){ gei.setInviteDelivered(true);
	 * gei.setUpdatedAt(Calendar.getInstance().getTime());
	 * groupEventInviteService.update(gei); } GroupEmail groupEmail =
	 * groupEmailsService.findById(groupEmailId); if(groupEmail!=null &&
	 * groupEmail.getEmailViewedDate()==null) {
	 * groupEmail.setEmailViewedDate(Calendar.getInstance().getTime());
	 * groupEmail.setUpdatedAt(Calendar.getInstance().getTime());
	 * groupEmailsService.update(groupEmail); } return
	 * request.getRequestURL().toString().replaceAll(request.getRequestURI(),
	 * request.getContextPath())
	 * +"/res/custom/images/email/generic/facebook.png"; }
	 */

	@RequestMapping(value = "/scan/{templateName}/{value}", method = RequestMethod.GET)
	public String scanMemberfromIdCard(Locale locale, Model model,
			@PathVariable String value, @PathVariable String templateName) {

		HashMap<String, Object> mdl = new HashMap<String, Object>();
		if (StringUtils.isBlank(value) || StringUtils.isBlank(templateName)) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "Invalid request");
			return "loadBlankPage";
		}
		GroupMember gm = new GroupMember();
		GroupEventInvite gei = null;
		try {
			gei = groupEventInviteService.findById(value);
			GroupEvents ge = groupEventsService.findByGroupEventCode(gei
					.getGroupEventCode());
			gm = gei.getGroupMember();
			
			if(ge.getExpiryDate()==null || ge.getExpiryDate().after(Calendar.getInstance().getTime())){
			List<GroupEventPass> groupEventPasses = groupEventPassesService
					.findByGroupEventInvite(gei);
			mdl.put("groupEventPasses", groupEventPasses);
			}
			mdl.put("groupEventInvite", gei);
			mdl.put("groupEvent", ge);
			mdl.put("groupMember", gm);

		}

		catch (Exception nf) {
			log.error("Unable to find any event for the id:" + value);
		}

		if (StringUtils.isBlank(gm.getSerialNumber())) {
			try {
				gm = groupMembersService.findById(value);
				model.addAttribute("groupMember", gm);
				mdl.put("groupMember", gm);
			} catch (Exception e) {
				model.addAttribute("error", true);
				model.addAttribute("errorMessage",
						"Member information Not found! Please contact your administrator");
				e.printStackTrace();
				return "loadBlankPage";
			}
		}

		GroupEmailTemplate get = groupEmailTemplateService
				.findbyTemplateName(templateName);
		if (get == null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "Invalid request");
			return "loadBlankPage";
		}
		String data = mailSenderUntypedActor
				.prepareEmailBody(templateName, mdl);
		model.addAttribute("content", data);
		return "loadBlankPage";
	}

	@RequestMapping(value = "/scanAndRegisterPasses", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String scanAndRegisterPasses(Locale locale,
			Model model, @RequestParam(required = false) String value,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String location,
			@RequestParam(required = false) String udid,
			@RequestParam(required = false) String templateName,
			@RequestParam(required = false) String autoRegister)
			throws Exception {

		HashMap<String, Object> hmap = new HashMap<String, Object>();
		hmap.put("newLine", "\n");
		GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
				.findbyTemplateName(templateName);
		if (gEmailTemplate == null) {
			throw new Exception(
					"Template not found for the provided template Name:"
							+ templateName);

		}
		if (StringUtils.isBlank(value)) {
			hmap.put("sound", "playFailureSound");
			hmap.put("alertTitle", "Insufficient details");
			hmap.put("status",
					"Either of templateName or value is missing.\ntemplateName:"
							+ templateName + "\nvalue:" + value);
			return mailSenderUntypedActor.prepareEmailBody(
					gEmailTemplate.getTemplateName(), hmap);
		}

		GroupEventPass gep = new GroupEventPass();
		gep = groupEventPassesService.findByPassBarcode(value);

		if (gep == null) {
			gep = groupEventPassesService.findByPassIdentifier(value);
		}
		int timeZoneDifferenceInHours = 0;

		if (StringUtils.isNotBlank(props.timeZoneDifferenceInHours)) {
			timeZoneDifferenceInHours = Integer
					.parseInt(props.timeZoneDifferenceInHours);
		}
		if (gep == null) {
			hmap.put("sound", "playFailureSound");
			hmap.put("alertTitle", "Not Found");
			hmap.put("status", "Barcode is not found in the system:" + value);
			return mailSenderUntypedActor.prepareEmailBody(
					gEmailTemplate.getTemplateName(), hmap);
		}
		hmap.put("groupEventPass", gep);
		if (gep.isPassInvalidated()) {
			hmap.put("sound", "playFailureSound");
			hmap.put("alertTitle", "Pass Invalidated");
			hmap.put(
					"status",
					"Barcode :"
							+ value
							+ " has been marked as invalid! May be it was lost or stolen");
			return mailSenderUntypedActor.prepareEmailBody(
					gEmailTemplate.getTemplateName(), hmap);

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
			hmap.put("sound", "playFailureSound");
			hmap.put("alertTitle", "Pass Inactive");
			hmap.put(
					"status",
					"Barcode :"
							+ gep.getPassBarcode()
							+ " is not yet active. It will be activated at "
							+ CommonUtils.printDateInHomeTimeZone(gep
									.getPassStartDate())
							+ " (i.e in "
							+ (diffDays < 1 ? (diffHours < 1 ? diffMins
									+ " Minutes" : diffHours + " Hours")
									: diffDays + " days") + ")");
			return mailSenderUntypedActor.prepareEmailBody(
					gEmailTemplate.getTemplateName(), hmap);

		}
		if (gep.getPassExpiryDate() != null
				&& gep.getPassExpiryDate().before(DateTime.now()// .plusHours(timeZoneDifferenceInHours)
						.toDate())) {
			hmap.put("sound", "playFailureSound");
			hmap.put("alertTitle", "Pass Expired");
			hmap.put(
					"status",
					"Barcode :"
							+ gep.getPassBarcode()
							+ " has expired at "
							+ CommonUtils.printDateInHomeTimeZone(gep
									.getPassExpiryDate()));
			return mailSenderUntypedActor.prepareEmailBody(
					gEmailTemplate.getTemplateName(), hmap);

		}

		try {

			if (gep.getGroupEventInvite() != null
					&& gep.getGroupMember() != null) {
				hmap.put("sound", "playFailureSound");
				hmap.put("alertTitle", "Pass already scanned");
				hmap.put("status", "Barcode :" + gep.getPassBarcode()
						+ " is already registered against another user "
						+ gep.getGroupMember().getFirstName() + " "
						+ gep.getGroupMember().getLastName());
				return mailSenderUntypedActor.prepareEmailBody(
						gEmailTemplate.getTemplateName(), hmap);

			}

			if (gep.getTrackingDate() != null) {
				hmap.put("sound", "playFailureSound");
				hmap.put("alertTitle", "Pass already scanned");
				hmap.put(
						"status",
						"Barcode :"
								+ gep.getPassBarcode()
								+ " is already scanned at "
								+ CommonUtils.printDateInHomeTimeZone(gep
										.getTrackingDate()));
				return mailSenderUntypedActor.prepareEmailBody(
						gEmailTemplate.getTemplateName(), hmap);
			}

			// gep.setSold(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

		try {
			if (StringUtils.isNotBlank(autoRegister)
					&& StringUtils.equalsIgnoreCase(autoRegister, "true")) {
				gep.setUpdatedAt(Calendar.getInstance().getTime());
				gep.setTrackingDate(Calendar.getInstance().getTime());
				groupEventPassesService.update(gep);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error in updating the Event pass";
		}

		if (gep != null) {
			hmap.put("sound", "playSuccessSound");
			hmap.put("alertTitle", "Event Pass Status");
			hmap.put("status", "Pass " + gep.getPassBarcode()
					+ " is successfully located!");

		}

		String resp = mailSenderUntypedActor.prepareEmailBody(
				gEmailTemplate.getTemplateName(), hmap);
		return resp;
	}

	@RequestMapping(value = "/scanGroupMemberRemotely", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String scanGroupMemberRemotely(Locale locale,
			Model model, @RequestParam(required = false) String value,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String location,
			@RequestParam(required = false) String udid,
			@RequestParam(required = false) String templateName,
			@RequestParam(required = false) String autoRegister,
			@RequestParam(required = false) String json,
			HttpServletRequest request) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ICODYScannerRO scannedData = null;
		try {
			scannedData = mapper.readValue(json, ICODYScannerRO.class);
			Object inputValue = null;
			String inputType = null;
			long count = 0;
			double money = 0.0;
			String text = "";

			if (scannedData != null && scannedData.getWorkflow() != null) {
				for (HashMap<String, Object> workflowItems : scannedData
						.getWorkflow()) {
					if (workflowItems.get(Key.type).equals(Key.input)) {
						inputValue = workflowItems.get(Key.text);
					}
					if (workflowItems.get(Key.type).equals(Key.text)) {
						String textType = (String) workflowItems.get(Key.text);
						if (StringUtils.isNotBlank(textType)
								&& StringUtils.containsIgnoreCase(
										Key.barcodeInputConstants, textType)) {
							inputType = textType;
						}
					}
				}
				if (inputValue != null && inputType != null) {
					if (StringUtils.equalsIgnoreCase("DOUBLE", inputType)) {
						try {
							money = Double.parseDouble(inputValue.toString());
						} catch (NumberFormatException nfe) {
						}

					}

					if (StringUtils.equalsIgnoreCase("LONG", inputType)) {
						try {

							count = Long.parseLong(inputValue.toString());
						} catch (NumberFormatException nfe) {
						}
					}

					if (StringUtils.equalsIgnoreCase("STRING", inputType)) {
						text = (String) inputValue;
					}
				}
			}
		} catch (Exception e) {
			log.error("Parsing of scanned object failed!");
		}

		// System.out.println(jsonMap[0]);
		// log.info(CommonUtils.printDateInHomeTimeZone(new Date()));
		boolean failureSound = true;
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		hmap.put("newLine", "\n");
		GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
				.findbyTemplateName(templateName);
		if (gEmailTemplate == null) {
			throw new Exception(
					"Template not found for the provided template Name:"
							+ templateName);

		}
		if (StringUtils.isBlank(templateName) || StringUtils.isBlank(value)) {
			hmap.put("sound", "playFailureSound");
			hmap.put("alertTitle", "Insufficient details");
			hmap.put("registrationStatus",
					"Either of templateName or value is missing.\ntemplateName:"
							+ templateName + "\nvalue:" + value);
			hmap.put("status",
					"Either of templateName or value is missing.\ntemplateName:"
							+ templateName + "\nvalue:" + value);
			return mailSenderUntypedActor.prepareEmailBody(
					gEmailTemplate.getTemplateName(), hmap);
		}

		// String memberDetails = "";
		// String eventDetails = "";
		String regoStatus = "";
		GroupEventInvite gei = groupEventInviteService
				.findByGroupEventInviteCode(value);

		GroupMember gm = null;// new GroupMember();
		if (gei != null) {
			gm = gei.getGroupMember();
			/*
			 * memberDetails = "Name:" + gm.getFirstName() + " " +
			 * gm.getLastName() + "\nEmail:" + gm.getPrimaryEmail() +
			 * "\nMembership End:" + (gm.getMembershipEndDate() != null ? (gm
			 * .getMembershipEndDate().toString().substring(0, 10)) :
			 * "Non Member") + "\nFamily:" + gm.getAdultCount() + " Adult(s), "
			 * + gm.getKidCount() + " kid(s)" + "\n";
			 */
		} else {
			try {
				gm = groupMembersService.findById(value);
				/*
				 * memberDetails = "Name:" + gm.getFirstName() + " " +
				 * gm.getLastName() + "\nEmail:" + gm.getPrimaryEmail() +
				 * "\nMembership End:" + (gm.getMembershipEndDate() != null ?
				 * (gm .getMembershipEndDate().toString().substring(0, 10)) :
				 * "Non Member") + "\nFamily:" + gm.getAdultCount() +
				 * " Adult(s), " + gm.getKidCount() + " kid(s)" + "\n";
				 */
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					gei = groupEventInviteService.findById(value);
					gm = gei.getGroupMember();
					/*
					 * memberDetails = "Name:" + gm.getFirstName() + " " +
					 * gm.getLastName() + "\nEmail:" + gm.getPrimaryEmail() +
					 * "\nMembership End:" + (gm.getMembershipEndDate() != null
					 * ? (gm .getMembershipEndDate().toString() .substring(0,
					 * 10)) : "Non Member") + "\nFamily:" + gm.getAdultCount() +
					 * " Adult(s), " + gm.getKidCount() + " kid(s)" + "\n";
					 */
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					/*
					 * addError(
					 * "Exception occured when trying to search for member with id:"
					 * + value, model);
					 */
					gm = groupMembersService.findbyMembershipIdentifier(value);
					if (gm == null) {
						// gm = new GroupMember();
						log.error("Unable to find member based on the value scanned from mobile:"
								+ value);
						regoStatus = "Unable to locate details based on the input provided - "
								+ value;
					} else {
						/*
						 * memberDetails = "Name:" + gm.getFirstName() + " " +
						 * gm.getLastName() + "\nEmail:" + gm.getPrimaryEmail()
						 * + "\nMembership End:" + (gm.getMembershipEndDate() !=
						 * null ? (gm .getMembershipEndDate().toString()
						 * .substring(0, 10)) : "Non Member") + "\nFamily:" +
						 * gm.getAdultCount() + " Adult(s), " + gm.getKidCount()
						 * + " kid(s)" + "\n";
						 */
					}
					// e1.printStackTrace();

				}

			}
		}

		// Check for Scanner needs to be put here because the groupCode is
		// required to identify the device.
		if (gm != null && StringUtils.isNotBlank(udid)) {
			ExternalScanner extScanner = imprintAndValidateScanner(
					gm.getGroupCode(), udid, scannedData);
			if (!CommonUtils.isValidDates(extScanner.getAccessStartDate(),
					extScanner.getAccessEndDate())) {
				hmap.put("sound", "playFailureSound");
				hmap.put("alertTitle", "Un-Authorized Access");
				hmap.put(
						"status",
						"Your device is not registered to perform this action! Please contact your system admin.");
				return mailSenderUntypedActor.prepareEmailBody(
						gEmailTemplate.getTemplateName(), hmap);
			}
		}

		if (gei != null) {

			GroupEvents gEvent = groupEventsService.findByGroupEventCode(gei
					.getGroupEventCode());
			hmap.put("groupEvent", gEvent);
			/*
			 * eventDetails = "Event:" + gEvent.getEventName() + "\nEvent Code:"
			 * + gei.getGroupEventInviteCode() +
			 * (StringUtils.isNotBlank(gei.getTransactionReference()) ?
			 * "\nTransaction No:" + gei.getTransactionReference() : "") +
			 * (gei.getTransactionDateTime() != null ? "\nTransaction Date:" +
			 * (gei.getTransactionDateTime().toString() .substring(0, 10)) : "")
			 * + ("\nTransaction Approved:" + (gei.isTransactionApproved() ?
			 * "YES" : "NO")) + "\nAmount Paid:" + gei.getPaidAmount() +
			 * "\nRSVD'd:" + (gei.isRsvpd() ? "YES" : "NO");
			 */
			if (gEvent != null
					&& gEvent.getEventDate() != null
					&& gEvent.getEventDate().before(
							DateTime.now().plusHours(-6).toDate())) {
				// Added - 6 hours to allow registration from start of the event
				// until another 6 hours.
				regoStatus = "This event occured on "
						+ CommonUtils.printDateInHomeTimeZone(gEvent
								.getEventDate())
						+ " and registration window is open upto 6 hours after the event! Please Verify.";
			} else if (gEvent != null
					&& gEvent.getEventDate() != null
					&& gEvent.getEventDate().after(
							DateTime.now().plusHours(6).toDate())) {
				regoStatus = "This event is in the future ("
						+ CommonUtils.printDateInHomeTimeZone(gEvent
								.getEventDate())
						+ ") and registration is allowed only 6 hours before the event! Please Verify.";
			} else {
				if (gei.isMarkAttended()) {
					/*
					 * addInfo(gei.getGroupMember().getFirstName() + " " +
					 * gei.getGroupMember().getLastName() +
					 * " is already registered for the event. Please verify! Amount paid for the event is "
					 * + gei.getPaidAmount(), model);
					 */
					regoStatus = "Member is already registered for the event! Please Verify.";
				} else {
					try {
						failureSound = false;
						if (StringUtils.isNotBlank(autoRegister)
								&& "true".equalsIgnoreCase(autoRegister)) {
							gei.setMarkAttended(true);
							gei.setUpdatedAt(Calendar.getInstance().getTime());
							groupEventInviteService.update(gei);
							regoStatus = gei.getGroupMember().getFirstName()
									+ " "
									+ gei.getGroupMember().getLastName()
									+ " is now successfully registered for the event!";
						}
						/*
						 * addSuccess( gei.getGroupMember().getFirstName() + " "
						 * + gei.getGroupMember().getLastName() +
						 * " has now been successfully registered for the event. Amount paid for the event is "
						 * + gei.getPaidAmount(), model);
						 */
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}

			}
		}

		// String registrationStatus = regoStatus + "\n"
		// + eventDetails;
		String sound = "playSuccessSound";
		String title = gEmailTemplate.getSubject();
		if (failureSound) {
			sound = "playFailureSound";
			// registrationStatus =
			// "Unable to locate details based on the input provided";
		}
		if (gei != null) {
			List<GroupEventInviteRSVP> geir = groupEventInviteRSVPService
					.findByGroupEventInvite(gei);
			if (geir != null && geir.size() > 0) {
				hmap.put("groupEventInviteRSVP", geir.get(0));
			}
		}
		hmap.put("sound", sound);
		hmap.put("alertTitle", title);
		// hmap.put("registrationStatus", registrationStatus);
		hmap.put("status", regoStatus);

		hmap.put("groupMember", gm);
		hmap.put("groupEventInvite", gei);
		// Its put above -- hmap.put("groupEvent", gEvent);

		String resp = mailSenderUntypedActor.prepareEmailBody(
				gEmailTemplate.getTemplateName(), hmap);
		return resp;
	}

	@RequestMapping(value = "/files/{path}", method = RequestMethod.POST)
	public String upload(@PathVariable String path,
			@RequestParam MultipartFile file, ModelMap model,
			@RequestParam(defaultValue = "0") int startFile,
			@RequestParam(defaultValue = "20") int pageSize,
			@ModelAttribute("date") Date date, Errors errors) throws Exception {

		String originalFilename = file.getOriginalFilename();
		if (file.isEmpty()) {
			errors.reject(
					"file.upload.empty",
					new Object[] { originalFilename },
					"File upload was empty for filename=["
							+ HtmlUtils.htmlEscape(originalFilename) + "]");
			list(model, startFile, pageSize);
			return "files";
		}

		try {
			FileInfo dest = fileService.createFile(path + "/"
					+ originalFilename);
			file.transferTo(fileService.getResource(dest.getPath()).getFile());
			fileService.publish(dest);
			model.put("uploaded", dest.getPath());
		} catch (IOException e) {
			errors.reject(
					"file.upload.failed",
					new Object[] { originalFilename },
					"File upload failed for "
							+ HtmlUtils.htmlEscape(originalFilename));
		} catch (Exception e) {
			String message = "File upload failed downstream processing for "
					+ HtmlUtils.htmlEscape(originalFilename);
			if (log.isDebugEnabled()) {
				log.debug(message, e);
			} else {
				log.info(message);
			}
			errors.reject("file.upload.failed.downstream",
					new Object[] { originalFilename }, message);
		}

		if (errors.hasErrors()) {
			list(model, startFile, pageSize);
			return "files";
		}

		return "redirect:files";

	}

	@RequestMapping(value = "/files", method = RequestMethod.POST)
	public String uploadRequest(@RequestParam String path,
			@RequestParam MultipartFile file, ModelMap model,
			@RequestParam(defaultValue = "0") int startFile,
			@RequestParam(defaultValue = "20") int pageSize,
			@ModelAttribute("date") Date date, Errors errors) throws Exception {
		return upload(path, file, model, startFile, pageSize, date, errors);
	}

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public void list(ModelMap model,
			@RequestParam(defaultValue = "0") int startFile,
			@RequestParam(defaultValue = "20") int pageSize) throws Exception {

		List<FileInfo> files = fileService.getFiles(startFile, pageSize);
		Collections.sort(files);
		model.put("files", files);

	}

	/*
	 * @ExceptionHandler(Exception.class) public ModelAndView
	 * exceptionHandler(Exception ex, Locale locale) { ModelAndView mav = new
	 * ModelAndView(View.error); StringBuilder stack = new StringBuilder();
	 * 
	 * 
	 * mav.addObject("exception", ex.getMessage()); for (StackTraceElement
	 * element : ex.getStackTrace()) { stack.append(element.toString());
	 * stack.append("\n"); }
	 * 
	 * mav.addObject("stack", stack.toString()); return mav; }
	 */

	private ExternalScanner imprintAndValidateScanner(String groupCode,
			String deviceUuid, ICODYScannerRO scanner) {
		ExternalScanner extScanner = externalScannerService.findBydeviceUuid(
				groupCode, deviceUuid);

		if (extScanner == null) {
			extScanner = new ExternalScanner();
			extScanner.setAccessStartDate(Calendar.getInstance().getTime());
			extScanner.setAccessEndDate(Calendar.getInstance().getTime());
			extScanner.setDeviceUuid(deviceUuid);
			extScanner.setGroupCode(groupCode);

		}
		if (scanner != null) {
			if (StringUtils.isBlank(extScanner.getDeviceName())) {
				extScanner.setDeviceName(scanner.getDeviceName());
			}
		}
		try {
			log.info("Scanner " + extScanner.getDeviceName() + " ("
					+ extScanner.getDeviceUuid() + ")"
					+ " tried to access the portal at this time!");
			extScanner.setLastAccessDate(Calendar.getInstance().getTime());
			extScanner.setUpdatedAt(Calendar.getInstance().getTime());
			extScanner = externalScannerService.insertOrUpdate(extScanner);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return extScanner;

	}
	

}
