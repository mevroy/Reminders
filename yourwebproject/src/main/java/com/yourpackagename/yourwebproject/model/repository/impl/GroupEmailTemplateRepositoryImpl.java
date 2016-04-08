/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.repository.GroupEmailTemplateRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupEmailTemplateRepositoryImpl extends
		BaseHibernateJpaRepository<GroupEmailTemplate, Long> implements GroupEmailTemplateRepository{

	public GroupEmailTemplate findbyTemplateName(String templateName) {
		return (GroupEmailTemplate)sessionFactory.getCurrentSession().createQuery("from GroupEmailTemplate get where get.templateName = ?").setString(0,
                templateName).uniqueResult();
	}

	public List<GroupEmailTemplate> findbyGroupCode(String groupCode) {
		return (List<GroupEmailTemplate>)sessionFactory.getCurrentSession().createQuery("from GroupEmailTemplate get where get.groupCode = ? order by get.groupEventCode , get.templateName asc").setString(0,
				groupCode).list();
	}

	public List<GroupEmailTemplate> findbyGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode) {
		return (List<GroupEmailTemplate>)sessionFactory.getCurrentSession().createQuery("from GroupEmailTemplate get where get.groupCode = ? and (get.groupEventCode = ? or get.groupEventCode is null or get.groupEventCode = '') order by get.groupEventCode , get.templateName asc").setString(0,
				groupCode).setString(1, groupEventCode).list();
	}

}
