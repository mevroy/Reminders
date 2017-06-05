package com.yourpackagename.yourwebproject.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yourpackagename.framework.exception.auth.UserPermissionException;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupUserRole;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.service.GroupLinkAccessService;
import com.yourpackagename.yourwebproject.service.GroupMainLinksService;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.GroupsService;

public class UserValidationInterceptor implements HandlerInterceptor {
	@Autowired
	public GroupsService groupsService;
	@Autowired
	public GroupLinkAccessService groupLinkAccessService;
	@Autowired
	public GroupMainLinksService groupMainLinksService;
	protected @Autowired Props props;
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView view)
			throws Exception {

		Groups group = (Groups) request.getSession().getAttribute(Key.group);
		Role role = Role.ANONYMOUS;
		if (group != null) {
			User user = (User) request.getSession().getAttribute(Key.user);
			if (user != null) {
				role = user.getLoggedInRole();
			}
			if (request.getSession().getAttribute(Key.groupMainLinks) == null) {
				List<GroupMainLink> gmlinks = groupMainLinksService
						.populateNavigationLinksObject(group, false, false,
								role);
				request.getSession(true)
						.setAttribute(Key.groupMainLinks, gmlinks);
			}
		}
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse arg1, Object handler) throws Exception {

		if (handler != null && handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			CheckPermission checkPermission = handlerMethod
					.getMethodAnnotation(CheckPermission.class);
			if (checkPermission == null) {
				checkPermission = handlerMethod.getBeanType().getAnnotation(
						CheckPermission.class);

			}
			if (checkPermission != null
					&& checkPermission.allowedRoles() != null
					&& checkPermission.allowedRoles().length > 0) {
				String groupSubLinkId = request.getParameter("groupSubLinkId");
				String groupLinkAccessId = request
						.getParameter("groupLinkAccessId");

				User user = (User) request.getSession().getAttribute(Key.user);
				// Groups group =
				// (Groups)request.getSession().getAttribute(Key.group);

				String path = request.getRequestURI().replaceFirst(
						request.getContextPath() + "/", "");
				String[] contextValues = !StringUtils.isBlank(path) ? path
						.split("/") : null;
				String groupCode = contextValues != null
						&& contextValues.length > 0 ? contextValues[0] : "";
				Groups group = groupsService.findByGroupCodeActive(groupCode);
				if (group != null) {
					request.getSession().setAttribute(Key.groupName,
							group.getGroupLongName());
					request.getSession().setAttribute(Key.groupCode, groupCode);
					request.getSession().setAttribute(Key.group, group);
					if ((StringUtils.isNotBlank(group.getCss()) && StringUtils
							.isNotBlank(props.cssSelectorValues))
							 && props.cssSelectorValues
									.indexOf(group.getCss()) != -1)
					{
						request.getSession().setAttribute("cssSelector", group.getCss()+".");
					}

				} else {
					request.getSession().invalidate();
					throw new UserPermissionException("Page Not Found");
				}
				String requestPath = contextValues != null
						&& contextValues.length > 1 ? contextValues[1] : "";

				boolean userLoggedIn = true;
				boolean hasAccess = false;
				boolean needToSignIn = true;
				if (user == null)
					userLoggedIn = false;
				if (user != null) {
					List<GroupUserRole> groupUserRoles = user
							.getGroupUserRoles();
					GroupUserRole currentLoggedInRole = null;
					if (groupUserRoles != null && groupUserRoles.size() > 0) {
						currentLoggedInRole = groupUserRoles.get(0);
					}

					if (currentLoggedInRole != null) {
						for (Role type : checkPermission.allowedRoles()) {
							if (currentLoggedInRole.getRole() == type
									&& currentLoggedInRole
											.getGroup()
											.getGroupCode()
											.equalsIgnoreCase(
													group.getGroupCode())
									&& groupLinkAccessService
											.isURLAccessibleForUser(
													requestPath,
													user,
													group,
													StringUtils
															.isNotBlank(groupSubLinkId))) {
								hasAccess = true;
								break;
							}
						}
					}

				}
				if (!hasAccess) {
					for (Role type : checkPermission.allowedRoles()) {
						if (Role.ANONYMOUS == type)  {
							needToSignIn = false;
							if(groupLinkAccessService.isActualURLAccessibleForAnonymousRole(requestPath, group, true)){
								hasAccess = true;
								break;
							}

						}
					}
				}

				if (userLoggedIn && !hasAccess)
					throw new UserPermissionException(
							"You do not have access to this page!");

				if (!userLoggedIn && needToSignIn) {
					arg1.sendRedirect("login");
				}
				
				if(!needToSignIn && !hasAccess)
				{
					throw new UserPermissionException(
							"This page is not active at the moment, please try again later!");
				}
			}

		}
		request.setAttribute(Key.startTime, System.currentTimeMillis());
		return true;
	}
}
