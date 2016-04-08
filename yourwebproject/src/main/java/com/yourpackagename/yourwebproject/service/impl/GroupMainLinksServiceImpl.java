/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccess;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.model.repository.FeedbackRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupLinkAccessRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupMainLinksRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupSubLinksRepository;
import com.yourpackagename.yourwebproject.service.FeedbackService;
import com.yourpackagename.yourwebproject.service.GroupMainLinksService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupMainLinksServiceImpl extends
		BaseJpaServiceImpl<GroupMainLink, Long> implements
		GroupMainLinksService {

	private @Autowired GroupMainLinksRepository groupMainLinksRepository;
	private @Autowired GroupSubLinksRepository groupSubLinksRepository;
	private @Autowired GroupLinkAccessRepository groupLinkAccessRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupMainLinksRepository;
		this.entityClass = GroupMainLink.class;
		this.baseJpaRepository.setupEntityClass(GroupMainLink.class);

	}

	public List<GroupMainLink> findByGroupCodeAndUser(String groupCode,
			User user, boolean enableFilter) {

		return groupMainLinksRepository.findByGroupCodeAndUser(groupCode, user,
				enableFilter);
	}

	public List<GroupMainLink> findByGroupCodeAndRoles(String groupCode,
			List<Role> roles) {

		return groupMainLinksRepository.findByGroupCodeAndRoles(groupCode,
				roles);
	}

	public List<GroupMainLink> populateNavigationLinksObject(
			List<GroupMainLink> groupMainLinks, Groups group,
			boolean includeExpired, boolean includeDisabled , Role role) {

		for (GroupMainLink groupMainLink : groupMainLinks) {
			List<GroupSubLink> subLinks = groupSubLinksRepository
					.findByGroupMainLink(groupMainLink, includeDisabled);
			Collections.sort(subLinks, GroupSubLink.linkOrderComparatorAsc);
			groupMainLink.setGroupSubLinksForUI(subLinks);
			for (GroupSubLink groupSubLink : groupMainLink
					.getGroupSubLinksForUI()) {
				List<GroupLinkAccess> groupLinkAccess = new ArrayList<GroupLinkAccess>(
						groupLinkAccessRepository
								.findByGroupSubLinkAndGroupAndRole(
										groupSubLink, group, includeExpired,
										role));
				groupSubLink.setGroupLinkAccessForUI(groupLinkAccess);
			}
		}
		return groupMainLinks;
	}

	public List<GroupMainLink> findAll(boolean includeDisabled) {
		// TODO Auto-generated method stub
		return groupMainLinksRepository.findAll(includeDisabled);
	}

	public List<GroupMainLink> populateNavigationLinksObject(Groups group,
			boolean includeExpired, boolean includeDisabled, Role role) {
		List<GroupMainLink> gmlinks = this.findAll(includeDisabled);
		Collections.sort(gmlinks,GroupMainLink.linkOrderComparatorAsc);
		return populateNavigationLinksObject(gmlinks, group, includeExpired, includeDisabled,
				role);
	}

}
