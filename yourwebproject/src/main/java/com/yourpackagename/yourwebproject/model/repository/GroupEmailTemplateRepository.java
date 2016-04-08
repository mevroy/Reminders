/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEmailTemplateRepository extends BaseJpaRepository<GroupEmailTemplate	,Long> {

	public GroupEmailTemplate findbyTemplateName(String templateName);
	public List<GroupEmailTemplate> findbyGroupCode(String groupCode);
	public List<GroupEmailTemplate> findbyGroupCodeAndGroupEventCode(String groupCode, String groupEventCode);
	
}
