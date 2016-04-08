/**
 * 
 */
package com.yourpackagename.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @author mdsouza
 * 
 */
public class BaseFormController extends SimpleFormController {

	
	public BaseFormController()
	{
		setCommandClass(null);
		setCommandName("user");
	}
	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		return null;
	}

	protected ModelAndView onSubmit(Object command, BindException bindException)
			throws Exception {
		return new ModelAndView(getSuccessView());
	}

}
