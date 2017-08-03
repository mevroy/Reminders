/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccess;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupLinkAccessRepository extends BaseJpaRepository<GroupLinkAccess, Long> {

	public List<GroupLinkAccess> findByGroupSubLinkAndGroupAndRole(GroupSubLink groupSubLink, Groups group,
			boolean includeExpired, Role role);
	
	public List<GroupLinkAccess> findByLinkURLAndGroupAndRole(String url, Groups group,
			boolean includeExpired, Role role);
	
	public List<GroupLinkAccess> findByGroupMainLinkAndGroupAndRole(GroupMainLink groupMainLink, Groups group,
			boolean includeExpired, Role role);
	
	public List<GroupLinkAccess> findByGroupSubLinkAndGroup(GroupSubLink groupSubLink, Groups group,
			boolean includeExpired);
}
