package com.yourpackagename.yourwebproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yourpackagename.framework.exception.auth.UserPermissionException;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.common.ValidateGroupPath;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.GroupsService;
import com.yourpackagename.yourwebproject.service.LoggerService;

public class AttributesPopulatingInterceptor implements HandlerInterceptor {
	@Autowired
	public LoggerService loggerService;
	public @Autowired GroupsService groupsService;
	public @Autowired GroupMembersService groupMembersService;

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse arg1, Object handler) throws Exception {

		if (request.getSession().getAttribute(Key.groupCode) == null) {
			String groupCode = request.getParameter(Key.groupCode);
			if (StringUtils.isNotBlank(groupCode)) {
				Groups group = groupsService.findByGroupCodeActive(groupCode);
				if (group != null) {
					request.getSession().setAttribute(Key.groupName,
							group.getGroupLongName());
					request.getSession().setAttribute(Key.groupCode, groupCode);
					request.getSession().setAttribute(Key.group, group);

				}
			}
		}

		GroupMember gm = (GroupMember) request.getSession().getAttribute(
				Key.groupMember);
		String serialNumber = request.getParameter(Key.serialNumber);
		if (StringUtils.isNotBlank(serialNumber)
				&& (gm == null || !StringUtils.equals(serialNumber,
						gm.getSerialNumber()))) {
			request.getSession().removeAttribute(Key.groupMember);
			if (StringUtils.isNotBlank(serialNumber)) {
				try {
					gm = groupMembersService.findById(serialNumber);
					if (gm != null) {
						request.getSession().setAttribute(Key.groupMember, gm);
					}
				} catch (Exception e) {

				}
			}
		}

		return true;
	}
}
