/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
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

import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.model.entity.GroupCronJob;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupSMSTemplate;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupCronJobService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.GroupSMSTemplateService;
import com.yourpackagename.yourwebproject.service.GroupsService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
public class GroupEventsController extends BaseWebAppController {
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupCronJobService groupCronJobService;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupSMSTemplateService groupSMSTemplateService;
	private @Autowired GroupsService groupsService;

	@RequestMapping(value = "/addGroupEvent", method = RequestMethod.GET)
	public String addGroupEvent(Locale locale, Model model,
			@PathVariable String groupCode) {
		GroupEvents groupEvent = new GroupEvents();
		groupEvent.setGroupCode(groupCode);
		model.addAttribute("groupEvent", groupEvent);
		return "addGroupEvent";
	}

	@RequestMapping(value = "/saveGroupEvent", method = RequestMethod.POST)
	public String saveGroupEvent(Locale locale, Model model,
			@ModelAttribute("groupEvent") GroupEvents groupEvent,
			BindingResult results) throws Exception {
		if (results.hasErrors()) {
			return "addGroupEvent";
		} else {
			GroupEvents groupE = groupEventsService.insert(groupEvent);
			addSuccess(groupE.getEventName() + " successfully added", model);
			model.addAttribute("groupEvent", new GroupEvents());
			return "addGroupEvent";
		}
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/viewAllGroupEvents", method = RequestMethod.GET)
	public String viewAllGroupEvents(Locale locale, Model model,
			@PathVariable String groupCode) {
		return "viewGroupEvents";
	}
	
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/viewAllGroupEventsTickets", method = RequestMethod.GET)
	public String viewAllGroupEventsTickets(Locale locale, Model model,
			@PathVariable String groupCode) {
		return "viewGroupEventTickets";
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/json/viewAllGroupEvents", method = RequestMethod.GET)
	public @ResponseBody List<GroupEvents> viewAllGroupEventsList(
			Locale locale, Model model, @PathVariable String groupCode) {
		return groupEventsService.findByGroupCode(groupCode, true);
	}

	@RequestMapping(value = "/addGroupEventCron", method = RequestMethod.GET)
	public String addGroupEventCron(Locale locale, Model model,
			@PathVariable String groupCode) {
		GroupCronJob groupCronJob = new GroupCronJob();
		groupCronJob.setGroupCode(groupCode);
		model.addAttribute("groupCronJob", groupCronJob);
		return "addGroupEventCron";
	}

	@RequestMapping(value = "/saveGroupEventCron", method = RequestMethod.POST)
	public String saveGroupEventCron(Locale locale, Model model,
			@ModelAttribute("groupCronJob") GroupCronJob groupCronJob,
			BindingResult results) throws Exception {
		if (results.hasErrors()) {
			return "addGroupEventCron";
		} else {
			GroupCronJob groupCron = groupCronJobService.insert(groupCronJob);
			addSuccess(
					"Cron Job \""
							+ groupCron.getJobDescription()
							+ "\" has been successfully added. Cron scheduler run once every 10 mins and your job should be scheduled within the next 10 minutes.",
					model);
			model.addAttribute("groupCronJob", new GroupCronJob());
			return "addGroupEventCron";
		}
	}

	@RequestMapping(value = "/addGroupEmailTemplate", method = RequestMethod.GET)
	public String addGroupEmailTemplate(Locale locale, Model model,
			@PathVariable String groupCode,
			@RequestParam(required = false) Long id) throws Exception {
		GroupEmailTemplate groupEmailTemplate = new GroupEmailTemplate();
		groupEmailTemplate.setGroupCode(groupCode);
		if (id != null && id > 0) {
			groupEmailTemplate = groupEmailTemplateService.findById(id);
		}
		model.addAttribute("groupEmailTemplate", groupEmailTemplate);
		return "addGroupEmailTemplate";
	}

	@RequestMapping(value = "/saveGroupEmailTemplate", method = RequestMethod.POST)
	public String saveGroupEmailTemplate(
			Locale locale,
			Model model,
			@ModelAttribute("groupEmailTemplate") GroupEmailTemplate groupEmailTemplate,
			BindingResult results) throws Exception {
		if (results.hasErrors()) {
			return "addGroupEmailTemplate";
		} else {
			groupEmailTemplate.setUpdatedAt(Calendar.getInstance().getTime());
			GroupEmailTemplate groupET = groupEmailTemplateService
					.insertOrUpdate(groupEmailTemplate);
			addSuccess("Email Template \"" + groupET.getTemplateName()
					+ "\" has been successfully added/updated.", model);
			GroupEmailTemplate ge = new GroupEmailTemplate();
			ge.setGroupCode(groupET.getGroupCode());
			model.addAttribute("groupEmailTemplate", ge);
			return "addGroupEmailTemplate";
		}
	}

	
	@RequestMapping(value = "/addGroupSMSTemplate", method = RequestMethod.GET)
	public String addGroupSMSTemplate(Locale locale, Model model,
			@PathVariable String groupCode,
			@RequestParam(required = false) Long id) throws Exception {
		GroupSMSTemplate groupSMSTemplate = new GroupSMSTemplate();
		groupSMSTemplate.setGroup(groupsService.findByGroupCode(groupCode));
		if (id != null && id > 0) {
			groupSMSTemplate = groupSMSTemplateService.findById(id);
		}
		model.addAttribute("groupSMSTemplate", groupSMSTemplate);
		return "addGroupSMSTemplate";
	}

	@RequestMapping(value = "/saveGroupSMSTemplate", method = RequestMethod.POST)
	public String saveGroupSMSTemplate(
			Locale locale,
			Model model,
			@PathVariable String groupCode,
			@ModelAttribute("groupSMSTemplate") GroupSMSTemplate groupSMSTemplate,
			BindingResult results) throws Exception {
		if (results.hasErrors()) {
			return "addGroupSMSTemplate";
		} else {
			groupSMSTemplate.setUpdatedAt(Calendar.getInstance().getTime());
			groupSMSTemplate.setGroup(groupsService.findByGroupCode(groupCode));
			GroupSMSTemplate groupSMSET = groupSMSTemplateService
					.insertOrUpdate(groupSMSTemplate);
			addSuccess("Email Template \"" + groupSMSET.getTemplateName()
					+ "\" has been successfully added/updated.", model);
			GroupSMSTemplate ge = new GroupSMSTemplate();
			ge.setGroup(groupSMSET.getGroup());
			model.addAttribute("groupSMSTemplate", ge);
			return "addGroupSMSTemplate";
		}
	}

	
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER })
	@RequestMapping(value = "/json/viewGroupEvents", method = RequestMethod.GET)
	public @ResponseBody List<HashMap<String, Object>> viewGroups(
			Locale locale, Model model, @PathVariable String groupCode,
			@RequestParam String memberCategoryCode,
			@RequestParam(required = false) boolean includeExpiredEvents) {

		List<GroupEvents> ge = new ArrayList<GroupEvents>();
		if ("ALL".equalsIgnoreCase(memberCategoryCode)) {
			if (includeExpiredEvents)
				ge = groupEventsService.findByGroupCode(groupCode,
						includeExpiredEvents);
			else
				ge = groupEventsService.findByGroupCode(groupCode);
		} else {
			if (includeExpiredEvents)
				ge = groupEventsService.findByGroupCodeAndMemberCategoryCode(
						groupCode, memberCategoryCode, includeExpiredEvents);
			else
				ge = groupEventsService.findByGroupCodeAndMemberCategoryCode(
						groupCode, memberCategoryCode);
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (GroupEvents groupEvents : ge) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("label", groupEvents.getEventName());
			hm.put("value", groupEvents.getEventCode());
			list.add(hm);
		}
		return list;
	}

	@RequestMapping(value = "/json/viewGroupEvents/{memberCategoryCode}", method = RequestMethod.GET)
	public @ResponseBody List<HashMap<String, Object>> viewGroupEventsByMemberCategoryCode(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String memberCategoryCode) {
		System.out
				.println("Danger...! Should not call method viewGroupEventsByMemberCategoryCode in GroupEventsController");
		List<GroupEvents> ge = groupEventsService
				.findByGroupCodeAndMemberCategoryCode(groupCode,
						memberCategoryCode);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (GroupEvents groupEvents : ge) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("label", groupEvents.getEventName());
			hm.put("value", groupEvents.getEventCode());
			list.add(hm);
		}
		return list;
	}

	@RequestMapping(value = "/viewAllGroupEmailTemplates", method = RequestMethod.GET)
	public String viewAllGroupEmailTemplates(Locale locale, Model model,
			@PathVariable String groupCode) {
		return "viewGroupEmailTemplates";
	}

	@RequestMapping(value = "/json/viewAllGroupEmailTemplates", method = RequestMethod.GET)
	public @ResponseBody List<GroupEmailTemplate> viewAllGroupEmailTemplatesList(
			Locale locale, Model model, @PathVariable String groupCode) {
		return groupEmailTemplateService.findbyGroupCode(groupCode);
	}

	@RequestMapping(value = "/viewAllGroupSMSTemplates", method = RequestMethod.GET)
	public String viewAllGroupSMSTemplates(Locale locale, Model model,
			@PathVariable String groupCode) {
		return "viewGroupSMSTemplates";
	}

	@RequestMapping(value = "/json/viewAllGroupSMSTemplates", method = RequestMethod.GET)
	public @ResponseBody List<GroupSMSTemplate> viewAllGroupSMSTemplatesList(
			Locale locale, Model model, @PathVariable String groupCode) {
		return groupSMSTemplateService.findbyGroupCode(groupCode);
	}

	
	@RequestMapping(value = "/json/viewGroupEmailTemplates", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, List<HashMap<String, Object>>> viewGroupEmailTemplates(
			Locale locale, Model model, @PathVariable String groupCode,
			@RequestParam String groupEventCode) {
		List<GroupEmailTemplate> get = new ArrayList<GroupEmailTemplate>();
		if ("ALL".equalsIgnoreCase(groupEventCode)) {
			get = groupEmailTemplateService.findbyGroupCode(groupCode);
		} else {
			get = groupEmailTemplateService.findbyGroupCodeAndGroupEventCode(
					groupCode, groupEventCode);
		}
		HashMap<String, String> eventName = new HashMap<String, String>();
		HashMap<String, List<HashMap<String, Object>>> optGroupHashMap = new HashMap<String, List<HashMap<String, Object>>>();

		for (GroupEmailTemplate groupEmailTemplate : get) {
			List<HashMap<String, Object>> listOfOptions = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("label", groupEmailTemplate.getTemplateName());
			hm.put("value", groupEmailTemplate.getTemplateName());
			String currentTemplateEventCode = StringUtils.defaultIfEmpty(groupEmailTemplate.getGroupEventCode(), "Default");
			if (eventName.containsKey(currentTemplateEventCode) && optGroupHashMap.containsKey(eventName.get(currentTemplateEventCode))) {
				listOfOptions.addAll(optGroupHashMap.get(eventName.get(currentTemplateEventCode)));
				listOfOptions.add(hm);
				optGroupHashMap.put(eventName.get(currentTemplateEventCode),
						listOfOptions);

			} else {
				String defaultOptGroup = currentTemplateEventCode;
				if (StringUtils.isNotBlank(groupEmailTemplate
						.getGroupEventCode())) {
					defaultOptGroup = groupEventsService.findByGroupEventCode(
							groupEmailTemplate.getGroupEventCode())
							.getEventName();
					
				}
				eventName.put(currentTemplateEventCode, defaultOptGroup);
				listOfOptions.add(hm);
				optGroupHashMap.put(defaultOptGroup, listOfOptions);
			}
		}

		return optGroupHashMap;
	}

	
	@RequestMapping(value = "/json/viewGroupSMSTemplates", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, List<HashMap<String, Object>>> viewGroupSMSTemplates(
			Locale locale, Model model, @PathVariable String groupCode,
			@RequestParam String groupEventCode) {
		List<GroupSMSTemplate> get = new ArrayList<GroupSMSTemplate>();
		if ("ALL".equalsIgnoreCase(groupEventCode)) {
			get = groupSMSTemplateService.findbyGroupCode(groupCode);
		} else {
			get = groupSMSTemplateService.findbyGroupCodeAndGroupEventCode(
					groupCode, groupEventCode);
		}
		HashMap<String, String> eventName = new HashMap<String, String>();
		HashMap<String, List<HashMap<String, Object>>> optGroupHashMap = new HashMap<String, List<HashMap<String, Object>>>();

		for (GroupSMSTemplate groupSMSTemplate : get) {
			List<HashMap<String, Object>> listOfOptions = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("label", groupSMSTemplate.getTemplateName());
			hm.put("value", groupSMSTemplate.getTemplateName());
			String currentTemplateEventCode = StringUtils.defaultIfEmpty(groupSMSTemplate.getGroupEventCode(), "Default");
			if (eventName.containsKey(currentTemplateEventCode) && optGroupHashMap.containsKey(eventName.get(currentTemplateEventCode))) {
				listOfOptions.addAll(optGroupHashMap.get(eventName.get(currentTemplateEventCode)));
				listOfOptions.add(hm);
				optGroupHashMap.put(eventName.get(currentTemplateEventCode),
						listOfOptions);

			} else {
				String defaultOptGroup = currentTemplateEventCode;
				if (StringUtils.isNotBlank(groupSMSTemplate
						.getGroupEventCode())) {
					defaultOptGroup = groupEventsService.findByGroupEventCode(
							groupSMSTemplate.getGroupEventCode())
							.getEventName();
					
				}
				eventName.put(currentTemplateEventCode, defaultOptGroup);
				listOfOptions.add(hm);
				optGroupHashMap.put(defaultOptGroup, listOfOptions);
			}
		}

		return optGroupHashMap;
	}

	/*
	 * @RequestMapping(value = "/json/viewGroupEmailTemplates", method =
	 * RequestMethod.GET) public @ResponseBody List<HashMap<String, Object>>
	 * viewGroupEmailTemplates( Locale locale, Model model, @PathVariable String
	 * groupCode,
	 * 
	 * @RequestParam String groupEventCode) { List<GroupEmailTemplate> get = new
	 * ArrayList<GroupEmailTemplate>(); if
	 * ("ALL".equalsIgnoreCase(groupEventCode)) { get =
	 * groupEmailTemplateService.findbyGroupCode(groupCode); } else { get =
	 * groupEmailTemplateService.findbyGroupCodeAndGroupEventCode( groupCode,
	 * groupEventCode); } List<HashMap<String, Object>> list = new
	 * ArrayList<HashMap<String, Object>>(); for (GroupEmailTemplate
	 * groupEmailTemplate : get) { HashMap<String, Object> hm = new
	 * HashMap<String, Object>(); hm.put("label",
	 * groupEmailTemplate.getTemplateName()); hm.put("value",
	 * groupEmailTemplate.getTemplateName()); list.add(hm); } return list; }
	 */
	@RequestMapping(value = "/json/viewGroupEmailTemplates/{groupEventCode}", method = RequestMethod.GET)
	public @ResponseBody List<HashMap<String, Object>> viewGroupEmailTemplatesByEventCode(
			Locale locale, Model model, @PathVariable String groupCode,
			@PathVariable String groupEventCode) {
		System.out
				.println("Danger...! Should not call method viewGroupEmailTemplatesByEventCode in GroupEventsController");
		List<GroupEmailTemplate> get = groupEmailTemplateService
				.findbyGroupCodeAndGroupEventCode(groupCode, groupEventCode);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (GroupEmailTemplate groupEmailTemplate : get) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("label", groupEmailTemplate.getTemplateName());
			hm.put("value", groupEmailTemplate.getTemplateName());
			list.add(hm);
		}
		return list;
	}
}
