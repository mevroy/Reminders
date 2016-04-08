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
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventInviteRepository;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventInviteServiceImpl extends
		BaseJpaServiceImpl<GroupEventInvite, String> implements
		GroupEventInviteService {

	@Autowired
	private GroupEventInviteRepository groupEventInviteRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventInviteRepository;
		this.entityClass = GroupEventInvite.class;
		this.baseJpaRepository.setupEntityClass(GroupEventInvite.class);

	}

	public List<GroupEventInvite> findByGroupCodeAndCategoryCodeAndEventCode(
			String groupCode, String memberCategoryCode, String eventCode) {
		return groupEventInviteRepository
				.findByGroupCodeAndCategoryCodeAndEventCode(groupCode,
						memberCategoryCode, eventCode);
	}

	public List<GroupEventInvite> findByGroupCodeAndEventCode(String groupCode,
			String eventCode) {
		return groupEventInviteRepository.findByGroupCodeAndEventCode(
				groupCode, eventCode);
	}

	public List<GroupEventInvite> findByGroupMember(GroupMember groupMember) {
		return groupEventInviteRepository.findByGroupMember(groupMember);
	}

	public GroupEventInvite findByGroupMemberAndGroupEvent(
			GroupMember groupMember, GroupEvents groupEvent) {
		return groupEventInviteRepository.findByGroupMemberAndGroupEvent(groupMember, groupEvent);
	}

	public GroupEventInvite findByGroupEventInviteCode(
			String groupEventInviteCode) {
		return groupEventInviteRepository.findByGroupEventInviteCode(groupEventInviteCode);
	}

}
