/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.Groups;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupsRepository extends BaseJpaRepository<Groups, Long> {

	public Groups findByGroupCode(String groupCode);
	public Groups findByGroupCodeActive(String groupCode);
	public List<Groups> findGroups();
	
}
