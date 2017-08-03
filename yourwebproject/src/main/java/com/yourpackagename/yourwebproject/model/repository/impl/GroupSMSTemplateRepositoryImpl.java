/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupSMSTemplate;
import com.yourpackagename.yourwebproject.model.repository.GroupSMSTemplateRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupSMSTemplateRepositoryImpl extends
		BaseHibernateJpaRepository<GroupSMSTemplate, Long> implements GroupSMSTemplateRepository{

	public GroupSMSTemplate findbyTemplateName(String templateName) {
		return (GroupSMSTemplate)sessionFactory.getCurrentSession().createQuery("from GroupSMSTemplate get where get.templateName = ?").setString(0,
                templateName).uniqueResult();
	}

	public List<GroupSMSTemplate> findbyGroupCode(String groupCode) {
		return (List<GroupSMSTemplate>)sessionFactory.getCurrentSession().createQuery("from GroupSMSTemplate get where get.group.groupCode = ? order by get.groupEventCode , get.templateName asc").setString(0,
				groupCode).list();
	}

	public List<GroupSMSTemplate> findbyGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode) {
		return (List<GroupSMSTemplate>)sessionFactory.getCurrentSession().createQuery("from GroupSMSTemplate get where get.group.groupCode = ? and (get.groupEventCode = ? or get.groupEventCode is null or get.groupEventCode = '') order by get.groupEventCode , get.templateName asc").setString(0,
				groupCode).setString(1, groupEventCode).list();
	}

}
