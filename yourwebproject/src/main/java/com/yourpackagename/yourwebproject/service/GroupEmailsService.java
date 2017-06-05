/**
 * 
 */
package com.yourpackagename.yourwebproject.service;


import java.util.List;
import java.util.Map;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;


/**
 * @author mevan.d.souza
 *
 */
public interface GroupEmailsService extends BaseService<GroupEmail, String> {

	public List<GroupEmail> findEmailsToBeSent(String conversionToTimeZone);
	public List<GroupEmail> findEmailsByGroupCode(String groupCode);
	public List<GroupEmail> findEmailsByGroupEventCode(String groupEventCode);
	public List<GroupEmail> findEmailsByMemberCategoryCodeAndGroupEventCode(String memberCategoryCode, String groupEventCode);
	public List<GroupEmail> findUnassignedEmailsByGroupCode(String groupCode);
	public GroupEmail createEmail(GroupEmail groupEmail, Map<String, Object> modelMap) throws Exception;
}
