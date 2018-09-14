/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.ExternalScanner;
import com.yourpackagename.yourwebproject.model.entity.GroupInterests;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccess;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccessRole;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.LoggerType;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.ExternalScannerService;
import com.yourpackagename.yourwebproject.service.GroupInterestService;
import com.yourpackagename.yourwebproject.service.GroupsService;
import com.yourpackagename.yourwebproject.service.LoggerService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN })
@EnableLogging(loggerClass = "GroupsController")
public class GroupsController extends BaseWebAppController {

	private Logger log = LoggerFactory.getLogger(GroupsController.class);
	private @Autowired GroupsService groupsService;
	private @Autowired LoggerService loggerService;
	private @Autowired GroupInterestService groupInterestService;
	private @Autowired ExternalScannerService externalScannerService;

	@RequestMapping(value = "/addGroup", method = RequestMethod.GET)
	public String addGroup(Locale locale, Model model,
			@PathVariable String groupCode) {
		Groups group = new Groups();
		group.setGroupCode(groupCode);
		model.addAttribute("group", group);
		return "addGroup";
	}

	@RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
	public String saveGroup(Locale locale, Model model,
			@ModelAttribute("group") Groups groups, BindingResult results) {

		if (results.hasErrors()) {
			return "addGroup";
		} else {
			Groups addedGroup = new Groups();
			try {
				addedGroup = groupsService.insert(groups);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("group", new Groups());
			addSuccess(
					addedGroup.getGroupLongName() + " is successfully added",
					model);
		}

		return "addGroup";

	}

	@RequestMapping(value = "/json/viewGroups", method = RequestMethod.GET)
	public @ResponseBody List<HashMap<String, Object>> viewGroups(
			Locale locale, Model model) {
		List<Groups> groups = groupsService.findGroups();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (Groups group : groups) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("longName", group.getGroupLongName());
			hm.put("groupCode", group.getGroupCode());
			list.add(hm);
		}
		return list;
	}

	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/viewGroupInterests", method = RequestMethod.GET)
	public String  viewGroupInterests(Locale locale,
			Model model, @PathVariable String groupCode) {

		return "viewGroupInterests";
	}
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/viewAuditLogs", method = RequestMethod.GET)
	public String  viewAuditLogs(Locale locale,
			Model model, @PathVariable String groupCode) {

		return "viewAuditLogs";
	}
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupInterests", method = RequestMethod.GET)
	public @ResponseBody List<GroupInterests> viewGroupInterestsJson(Locale locale,
			Model model, @PathVariable String groupCode, @RequestParam(required=false) boolean includeAll) {
		List<GroupInterests> gI = groupInterestService
				.findByGroupCode(groupCode,includeAll);
		return gI;
	}
	
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/viewGroupScannerDevices", method = RequestMethod.GET)
	public String  viewGroupScannerDevices(Locale locale,
			Model model, @PathVariable String groupCode) {

		return "viewGroupScannerDevices";
	}
	
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/viewGroupScannerDevices", method = RequestMethod.GET)
	public @ResponseBody List<ExternalScanner> viewGroupScannerDevicesJson(Locale locale,
			Model model, @PathVariable String groupCode, @RequestParam(required=false) boolean includeAll) {
		List<ExternalScanner> eS = externalScannerService.findByGroupCode(groupCode);
		return eS;
	}
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/handleDeviceUpdates", method = RequestMethod.POST)
	public @ResponseBody String handleDeviceUpdates(Locale locale, Model model,
			@PathVariable String groupCode,
			@ModelAttribute("externalScanner") ExternalScanner externalScanner,
			BindingResult results,
			@RequestParam(required = true) String operation) {
		try {
			if (Key.ADD.equalsIgnoreCase(operation)) {
				ExternalScanner eS = externalScannerService.insert(externalScanner);
				return Key.SUCCESS;

			}
			if (Key.UPDATE.equalsIgnoreCase(operation)) {
				ExternalScanner eS = externalScannerService.findById(externalScanner.getId());
				eS.setAccessStartDate(externalScanner.getAccessStartDate());
				eS.setAccessEndDate(externalScanner.getAccessEndDate());
				eS.setDeviceName(externalScanner.getDeviceName());
				eS.setDeviceOwner(externalScanner.getDeviceOwner());
				eS.setUpdatedAt(Calendar.getInstance().getTime());
				externalScannerService.update(eS);
				return Key.SUCCESS;
			}
			if (Key.DELETE.equalsIgnoreCase(operation)) {
				ExternalScanner eS = externalScannerService.findById(externalScanner.getId());
				eS.setUpdatedAt(Calendar.getInstance().getTime());
				eS.setAccessEndDate(Calendar.getInstance().getTime());
				externalScannerService.update(eS);
				return Key.SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occured during your "+operation+" operation."+e.toString();
		}
		return "Error occured during your "+operation+" operation";
	}
	
	
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/updateGroupInterests", method = RequestMethod.POST)
	public @ResponseBody String viewGroupInterests(Locale locale,
			Model model, @PathVariable String groupCode, @ModelAttribute GroupInterests groupInterest) {

		groupInterest.setUpdatedAt(Calendar.getInstance().getTime());
		groupInterest.setGroupCode(groupCode);
		try {
			groupInterestService.insertOrUpdate(groupInterest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
	@RequestMapping(value = "/json/auditLog", method = RequestMethod.GET)
	public @ResponseBody List<AuditLog> viewAuditLogJson(Locale locale,
			Model model, @PathVariable String groupCode) {
		List<AuditLog> logs = loggerService
				.findByGroupCode(groupCode);
		return logs;
	}
}
