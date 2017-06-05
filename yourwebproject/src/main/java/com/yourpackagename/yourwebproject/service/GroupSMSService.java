/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupContent;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;


/**
 * @author mevan.d.souza
 *
 */
public interface GroupSMSService extends BaseService<GroupSMS, String> {

	public List<GroupSMS> findByGroupCode(String groupCode);
	public GroupSMS findByMessageId(String messageId);
	public List<GroupSMS> findSMSByGroupEventCode(String groupEventCode);
	public List<GroupSMS> findSMSByMemberCategoryCodeAndGroupEventCode(String memberCategoryCode, String groupEventCode);
	public List<GroupSMS> findUnassignedSMSByGroupCode(String groupCode);
}
