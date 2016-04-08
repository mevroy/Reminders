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
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.model.repository.FeedbackRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupMainLinksRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupSubLinksRepository;
import com.yourpackagename.yourwebproject.service.FeedbackService;
import com.yourpackagename.yourwebproject.service.GroupMainLinksService;
import com.yourpackagename.yourwebproject.service.GroupSubLinksService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupSubLinksServiceImpl extends BaseJpaServiceImpl<GroupSubLink, String>
		implements GroupSubLinksService {

	private @Autowired GroupSubLinksRepository groupSubLinksRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupSubLinksRepository;
		this.entityClass = GroupSubLink.class;
		this.baseJpaRepository.setupEntityClass(GroupSubLink.class);

	}

	public List<GroupSubLink> findByGroupMainLink(GroupMainLink groupMainLink,
			boolean includeDisabled) {

		return groupSubLinksRepository.findByGroupMainLink(groupMainLink, includeDisabled);
	}





}
