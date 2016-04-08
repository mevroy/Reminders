/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupContent;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupContentRepository extends BaseJpaRepository<GroupContent, String> {

	public List<GroupContent> findByGroupCode(String groupCode, boolean includeExpired);
	
}
