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
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.repository.LoggerRepository;
import com.yourpackagename.yourwebproject.service.LoggerService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class LoggerServiceImpl extends BaseJpaServiceImpl<AuditLog, Long>
		implements LoggerService {

	private @Autowired LoggerRepository loggerRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = loggerRepository;
		this.entityClass = AuditLog.class;
		this.baseJpaRepository.setupEntityClass(AuditLog.class);

	}

	public List<AuditLog> findByGroupCode(String groupCode) {
		return loggerRepository.findByGroupCode(groupCode);
	}

	public List<AuditLog> findByUser(User user, String groupCode) {
		return loggerRepository.findByUser(user, groupCode);
	}

	public List<AuditLog> findByGroupCodeAndRequestURIAndMethod(String groupCode,
			String requestURI,  String method) {
		return loggerRepository.findByGroupCodeAndRequestURIAndMethod(groupCode, requestURI, method);
	}



}
