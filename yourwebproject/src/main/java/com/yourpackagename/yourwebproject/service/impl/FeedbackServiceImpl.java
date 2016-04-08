/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.FeedbackRepository;
import com.yourpackagename.yourwebproject.service.FeedbackService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class FeedbackServiceImpl extends BaseJpaServiceImpl<Feedback, Long>
		implements FeedbackService {

	private @Autowired FeedbackRepository feedbackRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = feedbackRepository;
		this.entityClass = Feedback.class;
		this.baseJpaRepository.setupEntityClass(Feedback.class);

	}

	public List<Feedback> findByGroupEventCode(String groupEventCode) {
		return feedbackRepository.findByGroupEventCode(groupEventCode);
	}

	public Feedback findByGroupEventInvite(GroupEventInvite groupEventInvite) {
		return feedbackRepository.findByGroupEventInvite(groupEventInvite);
	}

	public List<Feedback> findByGroupMember(GroupMember groupMember) {
		return feedbackRepository.findByGroupMember(groupMember);
	}

	public List<Feedback> findByMemberCategoryCodeAndGroupEventCode(
			String memberCategoryCode, String groupEventCode) {
		return feedbackRepository.findByMemberCategoryCodeAndGroupEventCode(memberCategoryCode, groupEventCode);
	}

}
