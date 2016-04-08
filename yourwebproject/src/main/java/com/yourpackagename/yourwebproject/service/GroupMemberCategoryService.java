/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupMemberCategory;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupMemberCategoryService extends BaseService<GroupMemberCategory, Long>{

	public List<GroupMemberCategory> findByGroupCode(String groupCode);
	public GroupMemberCategory findByMemberCategoryCode(String memberCategoryCode);
}
