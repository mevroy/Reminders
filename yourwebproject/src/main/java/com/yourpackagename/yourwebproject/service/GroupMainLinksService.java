/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupMainLinksService extends BaseService<GroupMainLink, Long> {

	public List<GroupMainLink> findByGroupCodeAndUser(String groupCode, User user, boolean enableFilter);
	public List<GroupMainLink> findByGroupCodeAndRoles(String groupCode, List<Role> roles);
	public List<GroupMainLink> findAll(boolean includeDisabled);
	public List<GroupMainLink> populateNavigationLinksObject(
			List<GroupMainLink> groupMainLinks, Groups group, boolean includeExpired, boolean includeDisabled, Role role);
	public List<GroupMainLink> populateNavigationLinksObject(
			Groups group, boolean includeExpired, boolean includeDisabled, Role role);
}
