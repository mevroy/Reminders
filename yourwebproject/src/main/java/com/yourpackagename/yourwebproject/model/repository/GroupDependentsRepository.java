/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupDependentsRepository extends BaseJpaRepository<GroupDependents, String> {

	public List<GroupDependents> findByGroupMember(GroupMember groupMember);
	public List<GroupDependents> findByGroupMemberSerialNumber(String serialNumber);
}
