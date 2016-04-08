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
import com.yourpackagename.yourwebproject.model.entity.RegisterInterest;
import com.yourpackagename.yourwebproject.model.repository.RegisterInterestRepository;
import com.yourpackagename.yourwebproject.service.RegisterInterestService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class RegisterInterestServiceImpl extends BaseJpaServiceImpl<RegisterInterest, Long	> implements RegisterInterestService {

	private @Autowired RegisterInterestRepository registerInterestRepository;
	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = registerInterestRepository;
		this.entityClass = RegisterInterest.class;
		this.baseJpaRepository.setupEntityClass(RegisterInterest.class);
		
	}

	public List<RegisterInterest> findByGroupCode(String groupCode,
			boolean includeAll) {
			return registerInterestRepository.findByGroupCode(groupCode, includeAll);
	}

}
