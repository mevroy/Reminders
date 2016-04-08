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
import com.yourpackagename.yourwebproject.model.entity.GroupInterests;
import com.yourpackagename.yourwebproject.model.repository.GroupInterestRepository;
import com.yourpackagename.yourwebproject.service.GroupInterestService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupInterestServiceImpl extends
		BaseJpaServiceImpl<GroupInterests, Long> implements
		GroupInterestService {

	@Autowired
	private GroupInterestRepository groupInterestRepository;

	@PostConstruct
	public void setupService() {

		this.baseJpaRepository = groupInterestRepository;
		this.entityClass = GroupInterests.class;
		this.baseJpaRepository.setupEntityClass(GroupInterests.class);

	}

	public List<GroupInterests> findByGroupCode(String groupCode, boolean includeAll) {
		return groupInterestRepository.findByGroupCode(groupCode, includeAll);
	}

	public GroupInterests findByInterestType(String groupCode,
			String interestType,boolean includeAll) {
		return groupInterestRepository.findByInterestType(groupCode,
				interestType,includeAll);
	}

	public List<GroupInterests> findAllByInterestType(String groupCode,
			String interestType,boolean includeAll) {
		return groupInterestRepository.findAllByInterestType(groupCode,
				interestType,includeAll);
	}
}
