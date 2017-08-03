/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupContent;
import com.yourpackagename.yourwebproject.model.repository.GroupContentRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupContentRepositoryImpl extends BaseHibernateJpaRepository<GroupContent, String> implements GroupContentRepository{

	public List<GroupContent> findByGroupCode(String groupCode, boolean includeExpired) {
		return (List<GroupContent>)sessionFactory.getCurrentSession().createQuery("from GroupContent g where g.group.groupCode = ? "+(includeExpired?"":" and ((g.expiryDate is null or g.expiryDate >= NOW()) and (g.startDate is null or (g.startDate) <= NOW()))")).setString(0,
                groupCode).list();
	}


}
