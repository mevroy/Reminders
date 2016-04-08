/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEmailTemplateService extends BaseService<GroupEmailTemplate, Long> {

	public GroupEmailTemplate findbyTemplateName(String templateName);
	public List<GroupEmailTemplate> findbyGroupCode(String groupCode);
	public List<GroupEmailTemplate> findbyGroupCodeAndGroupEventCode(String groupCode, String groupEventCode);
	
}
