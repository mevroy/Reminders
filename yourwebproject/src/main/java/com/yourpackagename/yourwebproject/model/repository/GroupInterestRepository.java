/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupInterests;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupInterestRepository extends BaseJpaRepository<GroupInterests, Long> {

	public List<GroupInterests> findByGroupCode(String groupCode, boolean includeAll);
	public GroupInterests findByInterestType(String groupCode,String interestType, boolean includeAll);
	public List<GroupInterests> findAllByInterestType(String groupCode,String interestType, boolean includeAll);

}
