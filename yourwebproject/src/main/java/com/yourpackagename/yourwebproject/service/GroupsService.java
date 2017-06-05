/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.Groups;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupsService extends BaseService<Groups, Long> {

	public Groups findByGroupCode(String groupCode);
	public Groups findByGroupCodeActive(String groupCode);
	public List<Groups> findGroups();
}
