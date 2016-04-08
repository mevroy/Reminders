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
import com.yourpackagename.yourwebproject.model.entity.GroupContent;
import com.yourpackagename.yourwebproject.model.repository.GroupContentRepository;
import com.yourpackagename.yourwebproject.service.GroupContentService;


/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupContentServiceImpl extends BaseJpaServiceImpl<GroupContent, String>
		implements GroupContentService {
	private @Autowired GroupContentRepository groupContentRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupContentRepository;
		this.entityClass = GroupContent.class;
		this.baseJpaRepository.setupEntityClass(GroupContent.class);
	}

	public List<GroupContent> findByGroupCode(String groupCode,
			boolean includeExpired) {
		return groupContentRepository.findByGroupCode(groupCode, includeExpired);
	}


}
