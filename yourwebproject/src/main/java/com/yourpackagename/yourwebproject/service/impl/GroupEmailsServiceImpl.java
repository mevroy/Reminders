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
import com.yourpackagename.yourwebproject.model.repository.GroupEmailsRepository;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEmailsServiceImpl extends BaseJpaServiceImpl<GroupEmail, String> implements GroupEmailsService {

	private @Autowired GroupEmailsRepository groupEmailsRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEmailsRepository;
		this.entityClass = GroupEmail.class;
		this.baseJpaRepository.setupEntityClass(GroupEmail.class);
		
	}

	public List<GroupEmail> findEmailsToBeSent(String conversionToTimeZone) {

		return groupEmailsRepository.findEmailsToBeSent(conversionToTimeZone);
	}

	public List<GroupEmail> findEmailsByGroupCode(String groupCode) {
		return groupEmailsRepository.findEmailsByGroupCode(groupCode);
	}


	public List<GroupEmail> findEmailsByMemberCategoryCodeAndGroupEventCode(
			String memberCategoryCode, String groupEventCode) {
		return groupEmailsRepository.findEmailsByMemberCategoryCodeAndGroupEventCode(memberCategoryCode, groupEventCode);
	}

	public List<GroupEmail> findEmailsByGroupEventCode(String groupEventCode) {
		// TODO Auto-generated method stub
		return groupEmailsRepository.findEmailsByGroupEventCode(groupEventCode);
	}

	public List<GroupEmail> findUnassignedEmailsByGroupCode(String groupCode) {

		return groupEmailsRepository.findUnassignedEmailsByGroupCode(groupCode);
	}

}
