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
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.repository.GroupsRepository;
import com.yourpackagename.yourwebproject.service.GroupsService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupsServiceImpl extends BaseJpaServiceImpl<Groups, Long>
		implements GroupsService {
	private @Autowired GroupsRepository groupsRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupsRepository;
		this.entityClass = Groups.class;
		this.baseJpaRepository.setupEntityClass(Groups.class);
	}

	public Groups findByGroupCode(String groupCode) {

		return groupsRepository.findByGroupCode(groupCode);
	}

	public Groups findByGroupCodeActive(String groupCode)
	{
		return groupsRepository.findByGroupCodeActive(groupCode);
	}
	
	public List<Groups> findGroups() {
		return groupsRepository.findGroups();
	}

}
