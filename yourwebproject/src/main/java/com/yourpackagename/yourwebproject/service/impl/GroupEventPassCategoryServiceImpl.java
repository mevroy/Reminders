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
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassCategoryRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassesRepository;
import com.yourpackagename.yourwebproject.service.GroupEventPassCategoryService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventPassCategoryServiceImpl extends BaseJpaServiceImpl<GroupEventPassCategory, String> implements GroupEventPassCategoryService{

	@Autowired
	private GroupEventPassCategoryRepository groupEventPassCategoryRepository;
	
	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventPassCategoryRepository;
		this.entityClass = GroupEventPassCategory.class;
		this.baseJpaRepository.setupEntityClass(GroupEventPassCategory.class);

	}

	public List<GroupEventPassCategory> findByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode,
			boolean includeNotAvailableForPurchase) {

		return groupEventPassCategoryRepository.findByGroupCodeAndGroupEventCode(groupCode, groupEventCode, includeNotAvailableForPurchase);
	}

	public List<GroupEventPassCategory> findByGroupCodeAndGroupEventCodeForMemberType(
			String groupCode, String groupEventCode,
			boolean includeInactive, boolean availableForPurchase, String[] isMember) {

		return groupEventPassCategoryRepository.findByGroupCodeAndGroupEventCodeForMemberType(groupCode, groupEventCode, includeInactive, availableForPurchase,  isMember);
	}
	
}
