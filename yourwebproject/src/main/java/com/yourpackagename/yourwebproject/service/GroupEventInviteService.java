/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventInviteService extends BaseService<GroupEventInvite, String> {

	public List<GroupEventInvite> findByGroupCodeAndCategoryCodeAndEventCode(String groupCode, String memberCategoryCode, String eventCode);
	public List<GroupEventInvite> findByGroupCodeAndEventCode(String groupCode, String eventCode);
	public List<GroupEventInvite> findByGroupMember(GroupMember groupMember);
	public GroupEventInvite findByGroupMemberAndGroupEvent(GroupMember groupMember, GroupEvents groupEvent);
	public GroupEventInvite findByGroupEventInviteCode(String groupEventInviteCode);
}
