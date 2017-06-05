/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupContent;
import com.yourpackagename.yourwebproject.model.entity.GroupInboundSMS;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;


/**
 * @author mevan.d.souza
 *
 */
public interface GroupInboundSMSService extends BaseService<GroupInboundSMS, String> {

	public List<GroupInboundSMS> findByGroupCode(String groupCode);
	public List<GroupInboundSMS> findByMessageId(String messageId);
}
