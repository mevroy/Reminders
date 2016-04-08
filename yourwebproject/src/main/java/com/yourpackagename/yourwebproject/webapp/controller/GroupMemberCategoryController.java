/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;




import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.model.entity.GroupMemberCategory;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupMemberCategoryService;


/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles={Role.SUPER_ADMIN, Role.ADMIN})
public class GroupMemberCategoryController extends BaseWebAppController {

	private Logger log = LoggerFactory.getLogger(GroupMemberCategoryController.class);
	private @Autowired GroupMemberCategoryService groupMemberCategoryService;

	@RequestMapping(value = "/addGroupMemberCategory", method = RequestMethod.GET)
	public String addGroupMemberCategory(Locale locale, Model model) {
		GroupMemberCategory gmc = new GroupMemberCategory();
		
		model.addAttribute("groupMemberCategory", gmc);
		return "addGroupMemberCategory";
	}

	@RequestMapping(value = "/saveGroupMemberCategory", method = RequestMethod.POST)
	public String saveGroupMemberCategory(Locale locale, Model model,
			@ModelAttribute("groupMemberCategory") GroupMemberCategory groupMemberCategory,
			BindingResult results) {
		GroupMemberCategory addedGroupMemberCategory = new GroupMemberCategory();
		if (results.hasErrors()) {
			return "addGroupMemberCategory";
		}
		try {
			addedGroupMemberCategory = groupMemberCategoryService.insert(groupMemberCategory);
			addSuccess(
					addedGroupMemberCategory.getMemberCategoryName()
							+ " successfully added", model);
			model.addAttribute("groupMemberCategory", new GroupMemberCategory());
		} catch (Exception e) {
			addAlert("Adding GroupMember Failed", model);
		}

		return "addGroupMemberCategory";

	}

	
	
	@CheckPermission(allowedRoles={Role.SUPER_ADMIN, Role.ADMIN, Role.SUPER_USER})
	@RequestMapping(value = "/json/viewGroupMemberCategories", method = RequestMethod.GET)
	public @ResponseBody List<HashMap<String, Object>> viewGroupMemberCategories(
			Locale locale, Model model, @PathVariable String groupCode) {
		String groupCd = (groupCode == null || groupCode.trim().length()<=0 || "ALL".equalsIgnoreCase(groupCode)) ? ""
				: groupCode;

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		List<GroupMemberCategory> groupMemberCategories = new ArrayList<GroupMemberCategory>();
		if (groupCd.trim().length() > 0)
			groupMemberCategories = groupMemberCategoryService.findByGroupCode(groupCode);
		for(GroupMemberCategory gmc : groupMemberCategories)
		{
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("memberCategoryCode", gmc.getMemberCategoryCode());
			hm.put("memberCategoryName", gmc.getMemberCategoryName());
			list.add(hm);
		}
		return list;
	}
}
