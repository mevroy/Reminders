/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupInboundSMS;


/**
 * @author mevan.d.souza
 *
 */
public interface GroupInboundSMSRepository extends BaseJpaRepository<GroupInboundSMS, String> {

	public List<GroupInboundSMS> findByGroupCode(String groupCode);
	public List<GroupInboundSMS> findByMessageId(String messageId);
	
}
