package com.yourpackagename.yourwebproject.webapp.controller;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.exception.auth.AuthCredentialsMissingException;
import com.yourpackagename.framework.exception.auth.AuthenticationFailedException;
import com.yourpackagename.framework.validation.Validity;
import com.yourpackagename.yourwebproject.actor.MailSenderActor;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.actor.SMSSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.EnableLogging;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.entity.GroupUserRole;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.model.entity.request.UserRO;
import com.yourpackagename.yourwebproject.service.GroupEmailAccountService;
import com.yourpackagename.yourwebproject.service.GroupMainLinksService;
import com.yourpackagename.yourwebproject.service.GroupsService;
import com.yourpackagename.yourwebproject.service.MailSenderWebAPIService;
import com.yourpackagename.yourwebproject.service.PushedApiService;
import com.yourpackagename.yourwebproject.service.SmsApiService;
import com.yourpackagename.yourwebproject.service.UserService;
import com.yourpackagename.yourwebproject.webapp.common.Route;
import com.yourpackagename.yourwebproject.webapp.common.View;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

/**
 * The Merchant entity registration and related pages handler
 *
 * @author: Y Kamesh Rao
 * @created: 4/19/12 8:48 AM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@Controller
@EnableLogging(loggerClass = "UserController")
public class UserController extends BaseWebAppController {
	private Logger log = LoggerFactory.getLogger(UserController.class);
	private @Autowired UserService userService;
	private @Autowired MailSenderActor mailSenderActor;
	private @Autowired MailSenderUntypedActor mailSenderUntypedActor;
	private @Autowired SMSSenderUntypedActor smsSenderUntypedActor;
	private @Autowired GroupMainLinksService groupMainLinksService;
	private @Autowired GroupsService groupsService;
	private @Autowired SmsApiService smsApiService;
	private @Autowired MailSenderWebAPIService mailSenderWebAPIService;
	private @Autowired GroupEmailAccountService groupEmailAccountService;
	//private @Autowired PushedApiService pushedApiService;
	/**
	 * Show registration page
	 *
	 * @param locale
	 * @param model
	 *
	 * @return
	 */
	@RequestMapping(value = Route.userRegistration, method = RequestMethod.GET)
	public String userRegistration(Locale locale, Model model) {
		model.addAttribute("registerUserForm", new UserRO());
		return View.userRegistration;
	}

	/**
	 * Show Login page
	 *
	 * @param locale
	 * @param model
	 *
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model,
			@RequestParam(required = false) String cssSelector) {
		if ((StringUtils.isBlank(cssSelector) || StringUtils
				.isBlank(props.cssSelectorValues))
				|| (StringUtils.isNotBlank(props.cssSelectorValues) && props.cssSelectorValues
						.indexOf(cssSelector) == -1)) {
			return "login";
		}
		request.getSession().setAttribute("cssSelector", cssSelector+".");
		return "login";
	}

	/**
	 * Login a merchant
	 *
	 * @param locale
	 * @param model
	 * @param userRO
	 *
	 * @return
	 */
	@RequestMapping(value = Route.loginUser, method = {RequestMethod.GET,RequestMethod.POST})
	public String loginMerchant(HttpServletResponse response , Locale locale, Model model,
			@ModelAttribute(Key.loginUserForm) UserRO userRO,
			@PathVariable String groupCode) {
		try {
			// Authenticate the credentials
			log.info("Date Time in Australia/Melbourne : "+CommonUtils.printDateInHomeTimeZone(new Date()));
			if ((userRO.username != null && userRO.username.trim().length() > 0)
					&& (userRO.password != null && userRO.password.trim()
							.length() > 0)) {
				//final User user = userService.findByUsername(userRO.username);
				final User user = userService.findByUsernameAndGroupCode(userRO.username, groupCode,true);
				log.info("User Found: " + user.getUserName());

				if (user.getPassWord().equals(
						User.hashPassword(userRO.password))) {
					log.info("Authenticated: " + user.getUserName());
/*					mailSenderWebAPIService.sendSmapleEmail(null, groupEmailAccountService.findByEmailAccountCode("MEVN"));
					try {

				        ByteArrayOutputStream output = new ByteArrayOutputStream();
				        output = CommonUtils.prefillPDF();

				        response.addHeader("Content-Type", "application/pdf"); 
				        response.addHeader("Content-Disposition", "attachment; filename=\"686788_Mevan.pdf\"");
				        response.getOutputStream().write(output.toByteArray());
				        return null;

				    } catch (Exception ex) {            
				        ex.printStackTrace();
				    }*/
					// Update the login count and other info
					userService.loginUser(user, request);
					//smsApiService.sendSmsNotification("0481370821", "Hello Mevan");

					// Store the user in session
					request.getSession(true).setAttribute(Key.user, user);
					//The main links are removed so that pre-login links go away and the new set of link are reloaded
					request.getSession().removeAttribute(Key.groupMainLinks);
					model.addAttribute("ls", true);
					//pushedApiService.sendPushedNotification("User Logged in notification : "+user.getUserName()+" at "+CommonUtils.printDateInHomeTimeZone(user.getCurrentLoginAt()));
					log.info("Date Time in Australia/Melbourne for Logged in user: "+CommonUtils.printDateInHomeTimeZone(user.getCurrentLoginAt()));
					return Key.redirect + "/" + groupCode + Route.dashboard;
				} else {
					log.info("User Authentication Failed: "
							+ user.getUserName());
					throw new AuthenticationFailedException(msg.aFailed,
							msg.aFailedCode);
				}
			} else {
				throw new AuthCredentialsMissingException(msg.aParamMissing,
						msg.aParamMissingCode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			addError(msg.loginError, model);
			//addError(e.getMessage(), model);
			return "login";
		}
	}

	/**
	 * Registers new users
	 *
	 * @param locale
	 * @param model
	 * @param userRO
	 *
	 * @return
	 */
	@RequestMapping(value = Route.registerUser, method = RequestMethod.POST, consumes = Key.formEncoded)
	public String registerUser(Locale locale, Model model,
			@ModelAttribute(Key.regUserForm) UserRO userRO,
			BindingResult results, @PathVariable String groupCode) {
		try {
			if (results.hasErrors()) {
				return View.userRegistration;
			}
			User u = userRO.user(props);
			Validity validity = userService.validate(u);
			if (validity.isValid()) {
				GroupUserRole gur = new GroupUserRole();
				gur.setGroupCode(groupCode);
				gur.setGroup(groupsService.findByGroupCode(groupCode));
				gur.setRole(Role.USER);
				gur.setStartDate(Calendar.getInstance().getTime());
				List<GroupUserRole> roles = new ArrayList<GroupUserRole>();
				roles.add(gur);
				u.setGroupUserRoles(roles);
				userService.registerUser(u, request);
				// Write a method to get the address from the RO Object so and a
				// service method to update the group user so that User Obj does
				// not need to have address fields.
				// mailSenderActor.sendUserEmailIdConfirmationMail(u);
				request.getSession(true).setAttribute(Key.userInSession, u);
				model.addAttribute(Key.isRegister, true);
				return Key.redirect + "/" + groupCode + Route.dashboard;
			} else {
				addError(msg.registerError, model);
				return View.userRegistration;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			addError(msg.registerError, model);
			addError(e.getMessage(), model);
			return View.userRegistration;
		}
	}

	/**
	 * Logs out a merchant by deleting the session
	 *
	 * @param locale
	 * @param model
	 *
	 * @return
	 */
	@RequestMapping(value = Route.logoutUser, method = RequestMethod.GET)
	public String logoutUser(Locale locale, Model model,
			@PathVariable String groupCode) {
		request.getSession().removeAttribute(Key.userInSession);
		request.getSession().invalidate();
		return "redirect:" + "/" + groupCode + Route.home;
	}

	/*
	 * Move below this all into a new controller like Group controller for
	 * clarity
	 */

}
