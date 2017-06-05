/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupSMSTemplate;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupSMSTemplateRepository extends BaseJpaRepository<GroupSMSTemplate	,Long> {

	public GroupSMSTemplate findbyTemplateName(String templateName);
	public List<GroupSMSTemplate> findbyGroupCode(String groupCode);
	public List<GroupSMSTemplate> findbyGroupCodeAndGroupEventCode(String groupCode, String groupEventCode);
	
}
