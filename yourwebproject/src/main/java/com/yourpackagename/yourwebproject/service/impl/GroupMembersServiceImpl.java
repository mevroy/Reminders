/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupMembersRepository;
import com.yourpackagename.yourwebproject.service.GroupMembersService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupMembersServiceImpl extends BaseJpaServiceImpl<GroupMember, String> implements GroupMembersService {

	private @Autowired GroupMembersRepository groupMembersRespoistory;
	
	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupMembersRespoistory;
		this.entityClass = GroupMember.class;
		this.baseJpaRepository.setupEntityClass(GroupMember.class);
		
	}

	public List<GroupMember> findByGroupCodeAndMemberCategoryCode(
			String groupCode, String membercategoryCode) {
		return groupMembersRespoistory.findByGroupCodeAndMemberCategoryCode(groupCode, membercategoryCode);
	}

	public List<GroupMember> findByGroupCode(String groupCode) {
		return groupMembersRespoistory.findByGroupCode(groupCode);
	}

	public List<GroupMember> executeGenericQuery(String hibernateQuery) {
		return groupMembersRespoistory.executeGenericQuery(hibernateQuery);
	}
	
	public List<List<GroupMember>> executeGenericQueryAsList(String hibernateQuery)
	{
		List<List<GroupMember>> list = new ArrayList<List<GroupMember>>();
		list.add(groupMembersRespoistory.executeGenericQuery(hibernateQuery));
		return list;
	}


	public List<GroupMember> findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(
			String groupCode, String membercategoryCode, String groupEventCode) {
		return groupMembersRespoistory.findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(groupCode, membercategoryCode, groupEventCode);
	}

	public List<GroupMember> findByGroupCodeNotExitingInGroupEventInvite(
			String groupCode, String groupEventCode) {
		return groupMembersRespoistory.findByGroupCodeNotExitingInGroupEventInvite(groupCode, groupEventCode);
	}

	public GroupMember findbyMembershipIdentifier(String membershipIdentifier) {

		return groupMembersRespoistory.findbyMembershipIdentifier(membershipIdentifier);
	}

	public List<GroupMember> findByAssociatedEmailForGroupMember(
			String emailAddress, String groupCode) {
		return groupMembersRespoistory.findByAssociatedEmailForGroupMember(emailAddress, groupCode);
	}

}
