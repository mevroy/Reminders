/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.Groups;
import com.yourpackagename.yourwebproject.model.repository.GroupsRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupsRepositoryImpl extends BaseHibernateJpaRepository<Groups, Long> implements GroupsRepository {

	public Groups findByGroupCode(String groupCode) {
		Query q = sessionFactory.getCurrentSession().createQuery("from Groups g where g.groupCode = ?").setString(0,
        		groupCode);
		Groups groups = (Groups)q.uniqueResult();
        return  groups;
	}

	public Groups findByGroupCodeActive(String groupCode)
	{
		Query q = sessionFactory.getCurrentSession().createQuery("from Groups g where g.groupCode = ? and g.startDate<=NOW() and g.expiryDate>=NOW()").setString(0,
        		groupCode);
		Groups groups = (Groups)q.uniqueResult();
        return  groups;
	}
	public List<Groups> findGroups() {
		Query q = sessionFactory.getCurrentSession().createQuery("from Groups g where g.startDate<=NOW() and g.expiryDate>=NOW()");
		List<Groups> groups = q.list();
        return  groups;
	}

}
