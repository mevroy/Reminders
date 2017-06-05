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
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;
import com.yourpackagename.yourwebproject.model.repository.GroupEmailActivityRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupEmailsRepository;
import com.yourpackagename.yourwebproject.service.GroupEmailActivityService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEmailActivityServiceImpl extends BaseJpaServiceImpl<GroupEmailActivity, Long> implements GroupEmailActivityService {

	private @Autowired GroupEmailActivityRepository groupEmailActivityRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEmailActivityRepository;
		this.entityClass = GroupEmailActivity.class;
		this.baseJpaRepository.setupEntityClass(GroupEmailActivity.class);
		
	}

	public List<GroupEmailActivity> findEmailActivitiesByEmailId(
			String groupEmailId) {
		
		return groupEmailActivityRepository.findEmailActivitiesByEmailId(groupEmailId);
	}



}
