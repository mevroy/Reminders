/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.RegisterInterest;
import com.yourpackagename.yourwebproject.model.repository.RegisterInterestRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class RegisterInterestRepositoryImpl extends
		BaseHibernateJpaRepository<RegisterInterest, Long> implements RegisterInterestRepository{

	public List<RegisterInterest> findByGroupCode(String groupCode,
			boolean includeAll) {
		Query q = sessionFactory.getCurrentSession().createQuery("from RegisterInterest r where r.groupCode = ? "+(includeAll?" or r.groupCode is null or r.groupCode = '' ":"")+" order by r.createdAt desc").setString(0,
        		groupCode);
		List<RegisterInterest> registeredInterests = (List<RegisterInterest>)q.list();
        return  registeredInterests;
	}

}
