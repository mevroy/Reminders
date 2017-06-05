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
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.repository.GroupSMSRepository;
import com.yourpackagename.yourwebproject.service.GroupSMSService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupSMSServiceImpl extends BaseJpaServiceImpl<GroupSMS, String>
		implements GroupSMSService {
	private @Autowired GroupSMSRepository groupSMSRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupSMSRepository;
		this.entityClass = GroupSMS.class;
		this.baseJpaRepository.setupEntityClass(GroupSMS.class);
	}

	public List<GroupSMS> findByGroupCode(String groupCode) {
		return groupSMSRepository.findByGroupCode(groupCode);
	}

	public GroupSMS findByMessageId(String messageId) {
		return groupSMSRepository.findByMessageId(messageId);
	}

	public List<GroupSMS> findSMSByGroupEventCode(String groupEventCode) {

		return groupSMSRepository.findSMSByGroupEventCode(groupEventCode);
	}

	public List<GroupSMS> findSMSByMemberCategoryCodeAndGroupEventCode(
			String memberCategoryCode, String groupEventCode) {
		return groupSMSRepository.findSMSByMemberCategoryCodeAndGroupEventCode(memberCategoryCode, groupEventCode);
	}

	public List<GroupSMS> findUnassignedSMSByGroupCode(String groupCode) {
		return groupSMSRepository.findUnassignedSMSByGroupCode(groupCode);
	}


}
