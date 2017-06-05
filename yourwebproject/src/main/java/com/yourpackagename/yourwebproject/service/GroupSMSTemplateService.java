/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupSMSTemplate;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupSMSTemplateService extends BaseService<GroupSMSTemplate, Long> {

	public GroupSMSTemplate findbyTemplateName(String templateName);
	public List<GroupSMSTemplate> findbyGroupCode(String groupCode);
	public List<GroupSMSTemplate> findbyGroupCodeAndGroupEventCode(String groupCode, String groupEventCode);
	
}
