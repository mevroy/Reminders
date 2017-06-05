/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.controller.BaseController;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.FeedbackService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupSMSService;
import com.yourpackagename.yourwebproject.service.GroupsService;
import com.yourpackagename.yourwebproject.service.SmsApiService;

/**
 * @author mdsouza
 *
 */
@Controller
@EnableLogging(loggerClass = "FeedbackController")
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
		Role.SUPER_USER, Role.USER, Role.ANONYMOUS })
public class FeedbackController extends BaseWebAppController {
	@Autowired
	private GroupEventInviteService groupEventInviteService;
	@Autowired
	private GroupEventsService groupEventsService;
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private GroupSMSService groupSMSService;

	@RequestMapping(value = "/groupEventFeedback", method = RequestMethod.GET)
	public String groupEventFeedbackRequest(Model model,
			@RequestParam(required = true) String groupEventInviteId,
			@RequestParam(required = false) String redirectURL,
			@PathVariable String groupCode) throws Exception {
		GroupEventInvite gei = null;
		if (StringUtils.isNotBlank(redirectURL)) {
			return "redirect:" + redirectURL;
		}
		try {
			gei = groupEventInviteService.findById(groupEventInviteId);
		} catch (Exception e) {
			logger.error("Unable to find event invite for id:"
					+ groupEventInviteId);
		}

		if (gei == null) {
			return Key.redirect + "/" + groupCode + "/newFeedback";
		}
		Feedback savedFeedBack = feedbackService.findByGroupEventInvite(gei);
		GroupEvents ge = groupEventsService.findByGroupEventCode(gei
				.getGroupEventCode());
		model.addAttribute("eventName", ge.getEventName());
		Feedback fb = new Feedback();
		fb.setName(gei.getGroupMember().getFirstName() + " "
				+ gei.getGroupMember().getLastName());
		fb.setEmail(gei.getGroupMember().getPrimaryEmail());
		fb.setGroupCode(gei.getGroupCode());
		fb.setGroupEventInviteId(groupEventInviteId);
		fb.setGroupEventCode(gei.getGroupEventCode());
		fb.setSerialNumber(gei.getGroupMember().getSerialNumber());
		if (savedFeedBack != null) {
			fb = savedFeedBack;
		}

		model.addAttribute("feedback", fb);
		return "newFeedback";
	}

	@RequestMapping(value = "/newFeedback", method = RequestMethod.GET)
	public String newFeedback(Model model,
			@RequestParam(required = false) String groupCode,
			@RequestParam(required = false) String groupEventCode,
			@RequestParam(required = false) String serialNumber) {
		Feedback fb = new Feedback();
		fb.setGroupCode(groupCode);
		fb.setGroupEventCode(groupEventCode);
		fb.setSerialNumber(serialNumber);
		model.addAttribute("feedback", fb);
		return "newFeedback";
	}

	@RequestMapping(value = "/saveFeedback", method = RequestMethod.POST)
	public String saveGroupEventInviteRSVP(Model model,
			@ModelAttribute("feedback") Feedback feedback) throws Exception {

		feedback.setUpdatedAt(Calendar.getInstance().getTime());
		Feedback newFb = feedbackService.insertOrUpdate(feedback);
		model.addAttribute("success", true);
		model.addAttribute(
				"successMessage",
				"You feedback/suggestion/question has been submitted successfully! Thanks once again");
		model.addAttribute("feedback", newFb);
		// return "redirect:/groupEventFeedback/"+newFb.getGroupEventInviteId();
		return "newFeedback";
	}

	@RequestMapping(value = "/json/saveFeedback", method = RequestMethod.POST)
	public @ResponseBody String saveUserInputs(Model model,
			@ModelAttribute("feedback") Feedback feedback) throws Exception {

		feedback.setUpdatedAt(Calendar.getInstance().getTime());
		try {
			Feedback newFb = feedbackService.insertOrUpdate(feedback);
			try {

				Groups group = groupsService.findByGroupCode(feedback
						.getGroupCode());
				if (group != null
						&& StringUtils.isNotBlank(group.getContactNumber())) {

					for (String phoneumber : CommonUtils.convertStringToList(
							group.getContactNumber(), ",")) {
						GroupSMS groupSMS = new GroupSMS();
						groupSMS.setMobileNumber(phoneumber);
						groupSMS.setBody(newFb.getComments());
						groupSMS.setGroupCode(group.getGroupCode());
						groupSMSService.insert(groupSMS);
					}
				}

			} catch (Exception e) {

			}
		} catch (Exception ex) {
			return "Sorry! There was an error processing your request. Please try again later.";
		}
		return "Your feedback/suggestion/question has been submitted successfully! Thanks once again";
	}
}
