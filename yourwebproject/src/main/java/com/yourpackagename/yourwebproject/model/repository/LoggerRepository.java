/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.User;

/**
 * @author mevan.d.souza
 *
 */
public interface LoggerRepository extends BaseJpaRepository<AuditLog, Long> {

	public List<AuditLog> findByGroupCode(String groupCode);
	public List<AuditLog> findByUser(User user,String groupCode);
	public List<AuditLog> findByGroupCodeAndRequestURIAndMethod(String groupCode, String requestURI, String method);
	
}
