/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import com.yourpackagename.yourwebproject.model.entity.GroupContent;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupContentService;
import com.yourpackagename.yourwebproject.service.GroupsService;

/**
 * @author mdsouza
 *
 */
@Controller
@EnableLogging(loggerClass = "GroupContentController")
@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN })
public class GroupContentController extends BaseWebAppController {
	private @Autowired GroupContentService groupContentService;
	private @Autowired GroupsService groupsService;
	private Logger log = LoggerFactory.getLogger(GroupContentController.class);
	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER, Role.USER, Role.ANONYMOUS })
	@RequestMapping(value = "/loadContent", method = RequestMethod.GET)
	public String loadContent(Model model,
			@RequestParam(required = true) String contentId,
			@PathVariable String groupCode) throws Exception {
		if (StringUtils.isNotBlank(contentId)) {
			try {
				GroupContent gc = groupContentService.findById(contentId);
				if (gc.isActive()) {
					model.addAttribute("content", gc.getPageContent());
					return "loadContentBasedPage";
				}
			} catch (Exception nf) {
				log.error("Unable to load content for the content ID:"+contentId);
			}
		}
		model.addAttribute("exception", "This content is not available.");
		return "error";

	}

	
	@CheckPermission(allowedRoles = { Role.SUPER_ADMIN, Role.ADMIN,
			Role.SUPER_USER, Role.USER, Role.ANONYMOUS })
	@RequestMapping(value = "/json/loadContent", method = RequestMethod.GET)
	public @ResponseBody List<GroupContent> loadContentJson(Model model,
			@RequestParam(required = true) String contentId,
			@PathVariable String groupCode) throws Exception {
		List<GroupContent> gcs = new ArrayList<GroupContent>();
		if (StringUtils.isNotBlank(contentId)) {
			try {
				GroupContent gc = groupContentService.findById(contentId);
				gcs.add(gc);
					return gcs;
				
			} catch (Exception nf) {
				log.error("Unable to load content for the content ID:"+contentId);
				return gcs;
			}
		}
		return gcs;

	}
	@RequestMapping(value = "/json/listAvailableContent", method = RequestMethod.GET)
	public @ResponseBody List<GroupContent> loadContent(Model model,
			@RequestParam(required = false) boolean includeExpired,
			@PathVariable String groupCode) throws Exception {
		return groupContentService.findByGroupCode(groupCode, includeExpired);
	}

	@RequestMapping(value = "/viewGroupContent", method = RequestMethod.GET)
	public String viewGroupContent(Model model,

	@PathVariable String groupCode) throws Exception {
		return "viewGroupContent";
	}

	@RequestMapping(value = "/handleGroupContent", method = RequestMethod.POST)
	public @ResponseBody String handleGroupContent(Model model,
			@RequestParam(required = true) String operation,
			@PathVariable String groupCode,
			@ModelAttribute GroupContent groupContent,
			BindingResult results)
			throws Exception {
		Groups group = groupsService.findByGroupCode(groupCode);
		try {
			if (Key.ADD.equalsIgnoreCase(operation)) {
				groupContent.setGroup(group);
				groupContent = groupContentService.insert(groupContent);
				groupContent.setContentURL("loadContent?contentId="
						+ groupContent.getContentId());
				groupContent.setUpdatedAt(Calendar.getInstance().getTime());
				groupContentService.insertOrUpdate(groupContent);
			} else if (Key.DELETE.equalsIgnoreCase(operation)) {
				GroupContent dbGc = groupContentService.findById(groupContent
						.getContentId());
				dbGc.setUpdatedAt(Calendar.getInstance().getTime());
				dbGc.setExpiryDate(Calendar.getInstance().getTime());
				groupContentService.update(dbGc);
			} else {
				GroupContent dbGc = groupContentService.findById(groupContent
						.getContentId());
				dbGc.setContentName(groupContent.getContentName());
				dbGc.setStartDate(groupContent.getStartDate());
				dbGc.setExpiryDate(groupContent.getExpiryDate());
				dbGc.setPageContent(groupContent.getPageContent());
				dbGc.setUpdatedAt(Calendar.getInstance().getTime());
				dbGc.setContentTitle(groupContent.getContentTitle());
				groupContentService.update(dbGc);
			}
		} catch (Exception ex) {
			return "Error occured during " + operation + " operation.\n"
					+ ex.getMessage();
		}

		return Key.SUCCESS;
	}

}
