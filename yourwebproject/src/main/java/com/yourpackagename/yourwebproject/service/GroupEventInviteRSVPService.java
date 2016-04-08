/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventInviteRSVPService extends BaseService<GroupEventInviteRSVP, String> {


	public List<GroupEventInviteRSVP> findByGroupCodeAndEventCode(String groupCode, String eventCode);
	public List<GroupEventInviteRSVP> findByGroupMemberAndGroupEventInvite(GroupMember groupMember, GroupEventInvite groupEventInvite);
	public List<GroupEventInviteRSVP> findByGroupEventInvite(GroupEventInvite groupEventInvite);
	public List<GroupEventInviteRSVP> findLatestRSVPsByMemberCategoryCodeAndEventCode(String groupCode, String memberCategoryCode, String groupEventCode);
	public List<GroupEventInviteRSVP> findLatestRSVPsByGroupCodeAndEventCode(String groupCode, String eventCode);

}
