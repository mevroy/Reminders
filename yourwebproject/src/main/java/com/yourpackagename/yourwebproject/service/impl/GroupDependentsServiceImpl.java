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
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupDependentsRepository;
import com.yourpackagename.yourwebproject.service.GroupDependentsService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupDependentsServiceImpl extends
		BaseJpaServiceImpl<GroupDependents, String> implements
		GroupDependentsService {

	@Autowired
	private GroupDependentsRepository groupDependentsRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupDependentsRepository;
		this.entityClass = GroupDependents.class;
		this.baseJpaRepository.setupEntityClass(GroupDependents.class);

	}

	public List<GroupDependents> findByGroupMember(GroupMember groupMember) {
		return groupDependentsRepository.findByGroupMember(groupMember);
	}

	public List<GroupDependents> findByGroupMemberSerialNumber(
			String serialNumber) {
		return groupDependentsRepository
				.findByGroupMemberSerialNumber(serialNumber);
	}

}
