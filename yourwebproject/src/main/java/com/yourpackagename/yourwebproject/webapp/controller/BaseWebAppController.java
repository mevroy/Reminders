package com.yourpackagename.yourwebproject.webapp.controller;

import com.yourpackagename.framework.aop.ExceptionHandlerAdvice;
import com.yourpackagename.framework.controller.BaseController;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.common.Message;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.common.ValidateGroupPath;
import com.yourpackagename.yourwebproject.custom.binders.CustomDateFormatBinder;
import com.yourpackagename.yourwebproject.custom.binders.CustomNumberEditorBinder;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.service.LoggerService;
import com.yourpackagename.yourwebproject.webapp.common.View;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Class to be extended by each of the controller implementing WebApp methods
 * and services for smooth access to the commonly used server response object
 * and integration with ExceptionHandlerAdvice interceptor to communicate the
 * exact exception to the frontend for better debugging.
 *
 * @author: Y Kamesh Rao
 * @created: 3/11/12 12:41 AM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@RequestMapping(value = { "/{groupCode}" })
public class BaseWebAppController extends BaseController {
	private Logger log = LoggerFactory.getLogger(BaseWebAppController.class);
	protected @Autowired ExceptionHandlerAdvice exceptionHandlerAdvice;
	protected @Autowired HttpServletRequest httpReq;
	protected @Autowired Message msg;
	protected @Autowired Props props;
	protected @Autowired Key key;
	protected @Autowired LoggerService loggerService;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		// final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// dateFormat.setLenient(false);
		// true passed to CustomDateEditor constructor means convert empty
		// String to null
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(
		// dateFormat, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new CustomDateFormatBinder(), true));

		binder.registerCustomEditor(int.class, new CustomNumberEditorBinder(
				Integer.class, false));
		binder.registerCustomEditor(double.class, new CustomNumberEditorBinder(
				Double.class, false));
		binder.registerCustomEditor(long.class, new CustomNumberEditorBinder(
				Long.class, true));
		binder.registerCustomEditor(boolean.class, new CustomBooleanEditor(
				null, null, true));
	}

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

	// Helper methods
	public void setup(Model model) {
		model.addAttribute("error", false);
		model.addAttribute("success", false);
		model.addAttribute("alert", false);
		model.addAttribute("info", false);
		model.addAttribute("loggedIn", false);
	}

	public void addError(String message, Model model) {
		model.addAttribute("error", true);
		model.addAttribute("errorMessage", message);
	}

	public void addSuccess(String message, Model model) {
		model.addAttribute("success", true);
		model.addAttribute("successMessage", message);
	}

	public void addAlert(String message, Model model) {
		model.addAttribute("alert", true);
		model.addAttribute("alertMessage", message);
	}

	public void addInfo(String message, Model model) {
		model.addAttribute("info", true);
		model.addAttribute("infoMessage", message);
	}

	public void addPopupModal(String title, String message, Model model) {
		model.addAttribute("popupModal", true);
		model.addAttribute("popupTitle", title);
		model.addAttribute("popupMessage", message);
	}

	public void addInfoWithAction(String infoWithActionHeading,
			String infoWithActionContent, String infoWithActionPrimaryAction,
			String infoWithActionPrimaryActionLink,
			String infoWithActionSecAction, String infoWithActionSecActionLink,
			Model model) {
		model.addAttribute("infoWithAction", true);
		model.addAttribute("infoWithActionHeading", infoWithActionHeading);
		model.addAttribute("infoWithActionContent", infoWithActionContent);
		model.addAttribute("infoWithActionPrimaryAction",
				infoWithActionPrimaryAction);
		model.addAttribute("infoWithActionPrimaryActionLink",
				infoWithActionPrimaryActionLink);

		if (infoWithActionSecAction != null)
			model.addAttribute("infoWithActionSecAction",
					infoWithActionSecAction);

		if (infoWithActionSecActionLink != null)
			model.addAttribute("infoWithActionSecActionLink",
					infoWithActionSecActionLink);
	}

	public void addUser(User user, Model model) {
		model.addAttribute("loggedIn", true);
		model.addAttribute("user", user);
	}

	public void loggedInUser(Model m, User u) {
		m.addAttribute("loggedIn", true);
		m.addAttribute("user", u);
	}

	public User getloggedInUser() {
		if (request.getSession().getAttribute(Key.userInSession) != null) {
			return (User) request.getSession().getAttribute(Key.userInSession);
		}

		else {
			return new User();
		}
	}

}
