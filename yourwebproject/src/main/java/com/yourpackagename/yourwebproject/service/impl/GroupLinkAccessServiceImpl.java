/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccess;
import com.yourpackagename.yourwebproject.model.entity.GroupLinkAccessRole;
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
import com.yourpackagename.yourwebproject.service.GroupLinkAccessService;
import com.yourpackagename.yourwebproject.service.GroupMainLinksService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupLinkAccessServiceImpl extends
		BaseJpaServiceImpl<GroupLinkAccess, Long> implements
		GroupLinkAccessService {

	private @Autowired GroupLinkAccessRepository groupLinkAccessRepository;
	private @Autowired GroupSubLinksRepository groupSubLinksRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupLinkAccessRepository;
		this.entityClass = GroupLinkAccess.class;
		this.baseJpaRepository.setupEntityClass(GroupLinkAccess.class);

	}

	public List<GroupLinkAccess> findByGroupSubLinkAndGroupAndRole(
			GroupSubLink groupSubLink, Groups group, boolean includeExpired,
			Role role) {

		return groupLinkAccessRepository.findByGroupSubLinkAndGroupAndRole(
				groupSubLink, group, includeExpired, role);
	}

	public List<GroupLinkAccess> findByGroupMainLinkAndGroupAndRole(
			GroupMainLink groupMainLink, Groups group, boolean includeExpired,
			Role role) {

		return groupLinkAccessRepository.findByGroupMainLinkAndGroupAndRole(
				groupMainLink, group, includeExpired, role);
	}

	public List<GroupLinkAccess> findByGroupSubLinkAndGroup(
			GroupSubLink groupSubLink, Groups group, boolean includeExpired) {

		return groupLinkAccessRepository.findByGroupSubLinkAndGroup(
				groupSubLink, group, includeExpired);
	}

	public List<GroupLinkAccess> findByLinkURLAndGroupAndRole(String url,
			Groups group, boolean includeExpired, Role role) {

		return groupLinkAccessRepository.findByLinkURLAndGroupAndRole(url,
				group, includeExpired, role);
	}

	public boolean isURLAccessibleForUser(String url, User user, Groups group, boolean subLinkAccess) {

		//if(subLinkAccess)

		if (!this.isBaseURLAccessible(url, user, group)) {
			return false;
		}
		List<GroupLinkAccess> glas = this.findByLinkURLAndGroupAndRole(url,
				group, true, user.getLoggedInRole());
		if (this.doValidLinkAccessRolesExist(glas)) {
			return true;
		}
		

		
		return CollectionUtils.isEmpty(glas);

	}

	private boolean isBaseURLAccessible(String url, User user, Groups group) {
		List<GroupSubLink> gSubs = groupSubLinksRepository.findByURL(url, true);
		for (GroupSubLink gSub : gSubs) {
			List<GroupLinkAccess> glas = this
					.findByGroupSubLinkAndGroupAndRole(gSub, group, true,
							user.getLoggedInRole());
			if (!gSub.isDisabled()) {
				if (this.doValidLinkAccessRolesExist(glas)) {
					return true;
				}
			}

		}

		return CollectionUtils.isEmpty(gSubs);
	}
	
	private boolean isBaseURLAccessibleForAnonymousRole(String url, Groups group) {
		List<GroupSubLink> gSubs = groupSubLinksRepository.findByURL(url, true);
		for (GroupSubLink gSub : gSubs) {
			List<GroupLinkAccess> glas = this
					.findByGroupSubLinkAndGroupAndRole(gSub, group, true,
							Role.ANONYMOUS);
			if (!gSub.isDisabled()) {
				if (CollectionUtils.isEmpty(glas) || this.doValidLinkAccessRolesExist(glas)) {
					return true;
				}
			}

		}

		return CollectionUtils.isEmpty(gSubs);
	}
	

	public boolean isActualURLAccessibleForAnonymousRole(String url, Groups group, boolean includeExpired)
	{
		

		if (!this.isBaseURLAccessibleForAnonymousRole(url, group)) {
			return false;
		}
		
		List<GroupLinkAccess> glas = this.findByLinkURLAndGroupAndRole(url,
				group, true, Role.ANONYMOUS);
		if (this.doValidLinkAccessRolesExist(glas)) {
			return true;
		}
		return CollectionUtils.isEmpty(glas);
	}
	
	private boolean doValidLinkAccessRolesExist(List<GroupLinkAccess> glas) {
		for (GroupLinkAccess gla : glas) {
			if (this.isValidDates(gla.getStartDate(), gla.getExpiryDate())) {
				for (GroupLinkAccessRole glar : gla.getGroupLinkAccessRoles()) {
					if (this.isValidDates(glar.getStartDate(),
							glar.getExpiryDate())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isValidStartDate(Date startDate) {
		return (startDate == null || startDate.before(Calendar.getInstance()
				.getTime()));

	}

	private boolean isValidEndDate(Date endDate) {
		return (endDate == null || endDate.after(Calendar.getInstance()
				.getTime()));

	}

	private boolean isValidDates(Date startDate, Date enddate) {
		if (this.isValidStartDate(startDate) && this.isValidEndDate(enddate))
			return true;
		return false;

	}

}
