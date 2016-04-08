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
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventInviteRSVPRepository;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventInviteRSVPServiceImpl extends
		BaseJpaServiceImpl<GroupEventInviteRSVP, String> implements
		GroupEventInviteRSVPService {
	
	@Autowired
	private GroupEventInviteRSVPRepository groupEventInviteRSVPRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventInviteRSVPRepository;
		this.entityClass = GroupEventInviteRSVP.class;
		this.baseJpaRepository.setupEntityClass(GroupEventInviteRSVP.class);

	}

	public List<GroupEventInviteRSVP> findByGroupCodeAndEventCode(
			String groupCode, String eventCode) {
		return groupEventInviteRSVPRepository.findByGroupCodeAndEventCode(
				groupCode, eventCode);
	}

	public List<GroupEventInviteRSVP> findByGroupMemberAndGroupEventInvite(
			GroupMember groupMember, GroupEventInvite groupEventInvite) {
		return groupEventInviteRSVPRepository
				.findByGroupMemberAndGroupEventInvite(groupMember,
						groupEventInvite);
	}

	public List<GroupEventInviteRSVP> findByGroupEventInvite(
			GroupEventInvite groupEventInvite) {
		return groupEventInviteRSVPRepository.findByGroupEventInvite(groupEventInvite);
	}

	public List<GroupEventInviteRSVP> findLatestRSVPsByMemberCategoryCodeAndEventCode(
			String groupCode, String memberCategoryCode, String groupEventCode) {
		return groupEventInviteRSVPRepository.findLatestRSVPsByMemberCategoryCodeAndEventCode(groupCode, memberCategoryCode, groupEventCode);
	}

	public List<GroupEventInviteRSVP> findLatestRSVPsByGroupCodeAndEventCode(
			String groupCode, String eventCode) {

		return groupEventInviteRSVPRepository.findLatestRSVPsByGroupCodeAndEventCode(groupCode, eventCode);
	}

}
