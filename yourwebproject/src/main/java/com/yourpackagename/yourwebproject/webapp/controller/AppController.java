package com.yourpackagename.yourwebproject.webapp.controller;

import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupsService;
import com.yourpackagename.yourwebproject.webapp.common.Route;
import com.yourpackagename.yourwebproject.webapp.common.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * App central controller to render home page
 *
 * @author: Y Kamesh Rao
 * @created: 2/28/12 7:54 AM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@Controller
@CheckPermission(allowedRoles={Role.SUPER_ADMIN, Role.ADMIN, Role.SUPER_USER, Role.USER,Role.ANONYMOUS})
@EnableLogging(loggerClass="AppController")
public class AppController extends BaseWebAppController {
	@Autowired
	public GroupsService groupsService;

	@RequestMapping(value = { Route.home }, method = RequestMethod.GET)
	public String home(Locale locale, Model model,
			@PathVariable("groupCode") String groupCode) {

		return View.home;
	}

}
