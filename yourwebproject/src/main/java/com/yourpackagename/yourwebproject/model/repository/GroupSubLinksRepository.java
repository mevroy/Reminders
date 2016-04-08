/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupSubLinksRepository extends BaseJpaRepository<GroupSubLink, String> {

	public List<GroupSubLink> findByGroupMainLink(GroupMainLink groupMainLink, boolean includeDisabled);
	public List<GroupSubLink> findByURL(String url, boolean includeDisabled);
	
}
