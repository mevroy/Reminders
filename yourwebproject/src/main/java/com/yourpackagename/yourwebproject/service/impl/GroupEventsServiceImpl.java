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
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.repository.GroupEventsRepository;
import com.yourpackagename.yourwebproject.service.GroupEventsService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventsServiceImpl extends BaseJpaServiceImpl<GroupEvents, Long> implements GroupEventsService{

	private @Autowired GroupEventsRepository groupEventsRepository;
	
	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventsRepository;
		this.entityClass = GroupEvents.class;
		this.baseJpaRepository.setupEntityClass(GroupEvents.class);
		
	}

	public List<GroupEvents> findByGroupCode(String groupCode) {
		return groupEventsRepository.findByGroupCode(groupCode);
	}

	public List<GroupEvents> findByGroupCodeAndMemberCategoryCode(
			String groupCode, String memberCategoryCode) {
		
		return groupEventsRepository.findByGroupCodeAndMemberCategoryCode(groupCode, memberCategoryCode);
	}

	public GroupEvents findByGroupEventCode(String groupEventCode) {
		return groupEventsRepository.findByGroupEventCode(groupEventCode);
	}

	public List<GroupEvents> findByGroupCode(String groupCode,
			boolean includeExpired) {
		return groupEventsRepository.findByGroupCode(groupCode, includeExpired);
	}

	public List<GroupEvents> findByGroupCodeAndMemberCategoryCode(
			String groupCode, String memberCategoryCode, boolean includeExpired) {
		return groupEventsRepository.findByGroupCodeAndMemberCategoryCode(groupCode, memberCategoryCode, includeExpired);
	}

}
