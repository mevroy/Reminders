/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface FeedbackService extends BaseService<Feedback, Long> {

	public List<Feedback> findByGroupEventCode(String groupEventCode);
	public List<Feedback> findByMemberCategoryCodeAndGroupEventCode(String memberCategoryCode, String groupEventCode);
	public Feedback findByGroupEventInvite(GroupEventInvite groupEventInvite);
	public List<Feedback> findByGroupMember(GroupMember groupMember);
}
