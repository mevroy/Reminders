package com.yourpackagename.yourwebproject.webapp.controller;

import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.UserService;
import com.yourpackagename.yourwebproject.webapp.common.Route;
import com.yourpackagename.yourwebproject.webapp.common.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

/**
 * Dashboard controller
 *
 * @author: Y Kamesh Rao
 * @created: 4/20/12 10:25 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@Controller
@CheckPermission(allowedRoles={Role.SUPER_ADMIN,Role.ADMIN, Role.SUPER_USER, Role.USER})
@EnableLogging(loggerClass="DashboardController")
public class DashboardController extends BaseWebAppController {
    private Logger log = LoggerFactory.getLogger(DashboardController.class);
    private @Autowired UserService userService;


    @RequestMapping(value = Route.dashboard, method = RequestMethod.GET)
    public String merchantDashboard(Locale locale, Model model,
                                    @RequestParam(value = Key.isLogin, required = false) boolean ls,
                                    @RequestParam(value = Key.isRegister, required = false) boolean rs,
                                    @RequestParam(value = Key.isEmailConfirmed, required = false) boolean ecs,
                                    @PathVariable String groupCode ) {
        if (rs) {
            addSuccess(msg.registerSuccess, model);
        } else if (ls)
            addSuccess(msg.loginSuccess, model);
        else if (ecs)
            addSuccess(msg.emailCnfSuccess, model);

        if (request.getSession(false) != null &&
                request.getSession(false).getAttribute(Key.userInSession) != null) {
            User u = (User) request.getSession(false).getAttribute(Key.userInSession);
            addUser(u, model);

            return View.dashboard;
        } else {
           // return Key.redirect + "/"+groupCode+Route.home;
        	return View.login;
        }
    }
}
