/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccess;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccessRole;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupLinkAccessService;
import com.yourpackagename.yourwebproject.service.GroupMainLinksService;
import com.yourpackagename.yourwebproject.service.GroupSubLinksService;
import com.yourpackagename.yourwebproject.service.GroupsService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN })
public class GroupNavigationLinksController extends BaseWebAppController {

	private @Autowired GroupMainLinksService groupMainLinksService;
	private @Autowired GroupSubLinksService groupSubLinksService;
	private @Autowired GroupLinkAccessService groupLinkAccessService;
	private @Autowired GroupsService groupsService;

	@RequestMapping(value = "/viewNavigationLinks", method = RequestMethod.GET)
	public String viewNavigationLinksAndRoleAccesses(Locale locale, Model model) {

		return "viewNavigationLinksAndRoleAccesses";
	}

	@RequestMapping(value = "/json/viewGroupMainLinks", method = RequestMethod.GET)
	public @ResponseBody List<GroupMainLink> viewGroupMainLinks(Locale locale,
			Model model) {
		List<GroupMainLink> gmLinks = groupMainLinksService.findAll(true);
		Collections.sort(gmLinks, GroupMainLink.linkOrderComparatorAsc);
		return gmLinks;
	}

	@RequestMapping(value = "/json/viewGroupSubLinks/{groupMainLinkId}", method = RequestMethod.GET)
	public @ResponseBody List<GroupSubLink> viewGroupSubLinks(Locale locale,
			Model model, @PathVariable Long groupMainLinkId) {
		try {
			GroupMainLink gml = groupMainLinksService.findById(groupMainLinkId);
			List<GroupSubLink> gsLinks = groupSubLinksService.findByGroupMainLink(gml, true);
			Collections.sort(gsLinks, GroupSubLink.linkOrderComparatorAsc);
			return gsLinks;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<GroupSubLink>();
	}

	@RequestMapping(value = "/json/viewGroupLinkAccesses/{groupSubLinkId}", method = RequestMethod.GET)
	public @ResponseBody List<GroupLinkAccess> viewGroupLinkAccesses(
			Locale locale, Model model, @PathVariable String groupSubLinkId,
			@PathVariable String groupCode) {
		GroupSubLink gsl;
		try {
			gsl = groupSubLinksService.findById(groupSubLinkId);
			Groups group = groupsService.findByGroupCode(groupCode);
			return groupLinkAccessService.findByGroupSubLinkAndGroup(gsl,
					group, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<GroupLinkAccess>();
	}

	@RequestMapping(value = "/json/viewGroupLinkRoleAccesses/{groupLinkAccessId}", method = RequestMethod.GET)
	public @ResponseBody List<GroupLinkAccessRole> viewGroupLinkRoleAccesses(
			Locale locale, Model model, @PathVariable Long groupLinkAccessId,
			@PathVariable String groupCode) {

		try {
			GroupLinkAccess gla = groupLinkAccessService
					.findById(groupLinkAccessId);
			return gla.getGroupLinkAccessRoles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<GroupLinkAccessRole>();
	}

	@RequestMapping(value = "/handleGroupMainLink", method = RequestMethod.POST)
	public @ResponseBody String handleGroupMainLink(Locale locale, Model model,
			@PathVariable String groupCode,
			@ModelAttribute("groupMainLink") GroupMainLink groupMainLink,
			BindingResult results,
			@RequestParam(required = true) String operation) {
		try {
			if (Key.ADD.equalsIgnoreCase(operation)) {
				groupMainLinksService.insert(groupMainLink);
				return Key.SUCCESS;

			}
			if (Key.UPDATE.equalsIgnoreCase(operation)) {
				GroupMainLink gmLink = groupMainLinksService.findById(groupMainLink.getId());
				gmLink.setLinkName(groupMainLink.getLinkName());
				gmLink.setLinkDescription(groupMainLink.getLinkDescription());
				gmLink.setLinkOrder(groupMainLink.getLinkOrder());
				gmLink.setCreatedBy(groupMainLink.getCreatedBy());
				gmLink.setUpdatedBy(groupMainLink.getUpdatedBy());
				gmLink.setCreatedAt(groupMainLink.getCreatedAt());
				gmLink.setDisabled(groupMainLink.isDisabled());
				gmLink.setOverrideChildUrlorJs(groupMainLink.isOverrideChildUrlorJs());
				gmLink.setUpdatedAt(Calendar.getInstance().getTime());
				groupMainLinksService.update(gmLink);
				return Key.SUCCESS;
			}
			if (Key.DELETE.equalsIgnoreCase(operation)) {
				GroupMainLink gmLink = groupMainLinksService.findById(groupMainLink.getId());
				gmLink.setDisabled(true);
				groupMainLinksService.update(gmLink);
				return Key.SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occured during your "+operation+" operation."+e.toString();
		}
		return "Error occured during your "+operation+" operation";
	}
	
	@RequestMapping(value = "/handleGroupSubLink", method = RequestMethod.POST)
	public @ResponseBody String handleGroupSubLink(Locale locale, Model model,
			@PathVariable String groupCode,
			@ModelAttribute("groupSubLink") GroupSubLink groupSubLink,
			BindingResult results,
			@RequestParam(required = true) String operation, @RequestParam(required=false) Long groupMainLinkId) {
		try {
			if (Key.ADD.equalsIgnoreCase(operation)) {
				GroupMainLink gmLink = groupMainLinksService.findById(groupMainLinkId);
				groupSubLink.setGroupMainLink(gmLink);
				groupSubLinksService.insert(groupSubLink);
				return Key.SUCCESS;

			}
			if (Key.UPDATE.equalsIgnoreCase(operation)) {
				GroupSubLink gsLink = groupSubLinksService.findById(groupSubLink.getId());
				gsLink.setLinkName(groupSubLink.getLinkName());
				gsLink.setLinkDescription(groupSubLink.getLinkDescription());
				gsLink.setLinkOrder(groupSubLink.getLinkOrder());
				gsLink.setLinkHref(groupSubLink.getLinkHref());
				gsLink.setLinkJavaScript(groupSubLink.getLinkJavaScript());
				//gsLink.setCreatedAt(groupSubLink.getCreatedAt());
				gsLink.setDisabled(groupSubLink.isDisabled());
				gsLink.setUpdatedAt(Calendar.getInstance().getTime());
				groupSubLinksService.update(gsLink);
				return Key.SUCCESS;
			}
			if (Key.DELETE.equalsIgnoreCase(operation)) {
				GroupSubLink gsLink = groupSubLinksService.findById(groupSubLink.getId());
				gsLink.setDisabled(true);
				groupSubLinksService.update(gsLink);
			//Doesnt work	groupSubLinksService.delete(gsLink);
				return Key.SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occured during your "+operation+" operation."+e.toString();
		}
		return "Error occured during your "+operation+" operation";
	}
	
	
	
	@RequestMapping(value = "/handleGroupLinkAccess", method = RequestMethod.POST)
	public @ResponseBody String handleGroupLinkAccess(Locale locale, Model model,
			@PathVariable String groupCode,
			@ModelAttribute("groupLinkAccess") GroupLinkAccess groupLinkAccess,
			BindingResult results,
			@RequestParam(required = true) String operation, @RequestParam(required=false) String groupSubLinkId) {
		try {
			if (Key.ADD.equalsIgnoreCase(operation)) {
				
				Groups group = groupsService.findByGroupCode(groupCode);
				groupLinkAccess.setGroupSubLinkId(groupSubLinkId);
				groupLinkAccess.setGroup(group);
				groupLinkAccess.setLinkHref(StringUtils.trimToNull(groupLinkAccess.getLinkHref()));
				groupLinkAccessService.insert(groupLinkAccess);
				return Key.SUCCESS;

			}
			if (Key.UPDATE.equalsIgnoreCase(operation)) {
				GroupLinkAccess gLinkAccess = groupLinkAccessService.findById(groupLinkAccess.getId());
				gLinkAccess.setStartDate(groupLinkAccess.getStartDate());
				gLinkAccess.setExpiryDate(groupLinkAccess.getExpiryDate());
				gLinkAccess.setLinkHref(StringUtils.trimToNull(groupLinkAccess.getLinkHref()));
				gLinkAccess.setLinkJavaScript(groupLinkAccess.getLinkJavaScript());
				gLinkAccess.setUpdatedAt(Calendar.getInstance().getTime());

				groupLinkAccessService.update(gLinkAccess);
				return Key.SUCCESS;
			}
			if (Key.DELETE.equalsIgnoreCase(operation)) {
				GroupLinkAccess glA = groupLinkAccessService.findById(groupLinkAccess.getId());
				glA.setExpiryDate(Calendar.getInstance().getTime());
				groupLinkAccessService.update(glA);
			//Doesnt work	groupSubLinksService.delete(gsLink);
				return Key.SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occured during your "+operation+" operation."+e.toString();
		}
		return "Error occured during your "+operation+" operation";
	}
	
	
	@RequestMapping(value = "/handleGroupLinkRoleAccess", method = RequestMethod.POST)
	public @ResponseBody String handleGroupLinkRoleAccess(Locale locale, Model model,
			@PathVariable String groupCode,
			@ModelAttribute("groupLinkAccessRole") GroupLinkAccessRole groupLinkAccessRole,
			BindingResult results,
			@RequestParam(required = true) String operation, @RequestParam(required=false) Long groupLinkAccessId) {
		try {
			if (Key.ADD.equalsIgnoreCase(operation)) {
				GroupLinkAccess gla = groupLinkAccessService.findById(groupLinkAccessId);
				gla.getGroupLinkAccessRoles().add(groupLinkAccessRole);
				groupLinkAccessService.update(gla);
				return Key.SUCCESS;

			}
			if (Key.UPDATE.equalsIgnoreCase(operation)) {
				GroupLinkAccess gLinkAccess = groupLinkAccessService.findById(groupLinkAccessId);
				for(GroupLinkAccessRole grole : gLinkAccess.getGroupLinkAccessRoles())
				{
					if(grole.getId() == groupLinkAccessRole.getId())
					{
						grole.setStartDate(groupLinkAccessRole.getStartDate());
						grole.setExpiryDate(groupLinkAccessRole.getExpiryDate());
						grole.setRole(groupLinkAccessRole.getRole());
						grole.setUpdatedAt(Calendar.getInstance().getTime());
						break;
					}
				}
				gLinkAccess.setGroupLinkAccessRoles(gLinkAccess.getGroupLinkAccessRoles());
				groupLinkAccessService.update(gLinkAccess);
				return Key.SUCCESS;
			}
			if (Key.DELETE.equalsIgnoreCase(operation)) {
				GroupLinkAccess gLinkAccess = groupLinkAccessService.findById(groupLinkAccessId);
				for(GroupLinkAccessRole grole : gLinkAccess.getGroupLinkAccessRoles())
				{
					if(grole.getId() == groupLinkAccessRole.getId())
					{

						grole.setExpiryDate(Calendar.getInstance().getTime());
						grole.setUpdatedAt(Calendar.getInstance().getTime());
						break;
					}
				}
				gLinkAccess.setGroupLinkAccessRoles(gLinkAccess.getGroupLinkAccessRoles());
				groupLinkAccessService.update(gLinkAccess);
				return Key.SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occured during your "+operation+" operation."+e.toString();
		}
		return "Error occured during your "+operation+" operation";
	}

	@RequestMapping(value = "/viewApplicationRoles", method = RequestMethod.GET)
	private @ResponseBody List<Role> viewApplicationRoles()
	{
		List<Role> roles = new ArrayList<Role>();
		for(Role role: Role.values())
		{
			roles.add(role);
		}
		return roles;
	}
}
