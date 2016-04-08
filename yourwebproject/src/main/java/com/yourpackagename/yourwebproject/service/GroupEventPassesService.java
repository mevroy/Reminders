/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventPassesService extends BaseService<GroupEventPass, String> {

	public List<GroupEventPass> findByGroupCodeAndGroupEventCode(String groupCode, String groupEventCode, boolean includeAll);
	public List<GroupEventPass> findByGroupEventInvite(GroupEventInvite groupEventInvite);
	public List<GroupEventPass> findByGroupMember(GroupMember groupMember);
	public List<GroupEventPass> findByGroupMemberAndGroupEventCode(GroupMember groupMember, String groupEventCode);
	public GroupEventPass findByPassIdentifier(String passIdentifier);
	public GroupEventPass findByPassBarcode(String passBarcode);
}
