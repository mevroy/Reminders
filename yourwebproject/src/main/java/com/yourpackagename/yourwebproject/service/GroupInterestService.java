/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupInterests;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupInterestService extends BaseService<GroupInterests, Long> {

	public List<GroupInterests> findByGroupCode(String groupCode, boolean includeAll);
	public GroupInterests findByInterestType(String groupCode,String interestType, boolean includeAll);
	public List<GroupInterests> findAllByInterestType(String groupCode,String interestType, boolean includeAll);
}
