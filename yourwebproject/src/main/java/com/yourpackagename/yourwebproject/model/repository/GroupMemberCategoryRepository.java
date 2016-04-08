/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupMemberCategory;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupMemberCategoryRepository extends BaseJpaRepository<GroupMemberCategory, Long> {
	public List<GroupMemberCategory> findByGroupCode(String groupCode);
	public GroupMemberCategory findByMemberCategoryCode(String memberCategoryCode);

}
