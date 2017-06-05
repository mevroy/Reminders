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
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentType;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPaymentTypeRepository;

import com.yourpackagename.yourwebproject.service.GroupEventPaymentTypeService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventPaymentTypeServiceImpl extends
		BaseJpaServiceImpl<GroupEventPaymentType, Long> implements
		GroupEventPaymentTypeService {


	private @Autowired GroupEventPaymentTypeRepository groupEventPaymentTypeRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventPaymentTypeRepository;
		this.entityClass = GroupEventPaymentType.class;
		this.baseJpaRepository
				.setupEntityClass(GroupEventPaymentType.class);

	}
	
	public List<GroupEventPaymentType> findByGroup(String groupCode,
			 boolean includeExpired) {
		return groupEventPaymentTypeRepository.findByGroup(groupCode, includeExpired);
	}


}
