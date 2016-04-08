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
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassesRepository;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventPassesServiceImpl extends BaseJpaServiceImpl<GroupEventPass, String> implements GroupEventPassesService{

	@Autowired
	private GroupEventPassesRepository groupEventPassesRepository;
	
	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventPassesRepository;
		this.entityClass = GroupEventPass.class;
		this.baseJpaRepository.setupEntityClass(GroupEventPass.class);

	}

	public List<GroupEventPass> findByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode, boolean includeAll) {
		return groupEventPassesRepository.findByGroupCodeAndGroupEventCode(groupCode, groupEventCode, includeAll);
	}

	public List<GroupEventPass> findByGroupEventInvite(
			GroupEventInvite groupEventInvite) {
		return groupEventPassesRepository.findByGroupEventInvite(groupEventInvite);
	}

	public List<GroupEventPass> findByGroupMember(GroupMember groupMember) {
		return groupEventPassesRepository.findByGroupMember(groupMember);
	}

	public GroupEventPass findByPassIdentifier(String passIdentifier) {
		return groupEventPassesRepository.findByPassIdentifier(passIdentifier);
	}

	public GroupEventPass findByPassBarcode(String passBarcode) {
		return groupEventPassesRepository.findByPassBarcode(passBarcode);
	}

	public List<GroupEventPass> findByGroupMemberAndGroupEventCode(
			GroupMember groupMember, String groupEventCode) {
		return groupEventPassesRepository.findByGroupMemberAndGroupEventCode(groupMember, groupEventCode);
	}

}
