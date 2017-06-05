/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccess;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupLinkAccessService extends BaseService<GroupLinkAccess, Long> {

	public List<GroupLinkAccess> findByGroupSubLinkAndGroupAndRole(GroupSubLink groupSubLink, Groups group,
			boolean includeExpired, Role role);
	
	public List<GroupLinkAccess> findByGroupMainLinkAndGroupAndRole(GroupMainLink groupMainLink, Groups group,
			boolean includeExpired, Role role);

	public List<GroupLinkAccess> findByGroupSubLinkAndGroup(GroupSubLink groupSubLink, Groups group,
			boolean includeExpired);
	
	public List<GroupLinkAccess> findByLinkURLAndGroupAndRole(String url, Groups group,
			boolean includeExpired, Role role);
	
	public boolean isURLAccessibleForUser(String url, User user, Groups group, boolean subLinkAccess);
	
	public boolean isActualURLAccessibleForAnonymousRole(String url, Groups group, boolean includeExpired);
}
