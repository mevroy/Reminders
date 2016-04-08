/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupDependentsService extends BaseService<GroupDependents, String> {

	public List<GroupDependents> findByGroupMember(GroupMember groupMember);
	public List<GroupDependents> findByGroupMemberSerialNumber(String serialNumber);

}
