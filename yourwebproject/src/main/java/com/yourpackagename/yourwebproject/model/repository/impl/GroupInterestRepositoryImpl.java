/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupInterests;
import com.yourpackagename.yourwebproject.model.repository.GroupInterestRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupInterestRepositoryImpl extends
		BaseHibernateJpaRepository<GroupInterests, Long>  implements GroupInterestRepository{

	public List<GroupInterests> findByGroupCode(String groupCode, boolean includeAll) {
		return(List<GroupInterests>)sessionFactory.getCurrentSession().createQuery("from GroupInterests gi where gi.groupCode = ? "+(includeAll?"":" and (gi.startDate is null or gi.startDate<=NOW()) and (gi.expiryDate is null or gi.expiryDate>=NOW())")).setString(0,
				groupCode).list();
	}

	public GroupInterests findByInterestType(String groupCode,String interestType, boolean includeAll) {
		return(GroupInterests)sessionFactory.getCurrentSession().createQuery("from GroupInterests gi where gi.groupCode = ? and gi.interestType=? "+(includeAll?"":" and (gi.startDate is null or gi.startDate<=NOW()) and (gi.expiryDate is null or gi.expiryDate>=NOW())")).setString(0,
				groupCode).setString(1, interestType).uniqueResult();
	}

	public List<GroupInterests> findAllByInterestType(String groupCode,String interestType, boolean includeAll) {
		return(List<GroupInterests>)sessionFactory.getCurrentSession().createQuery("from GroupInterests gi where gi.groupCode = ? and gi.interestType=? "+(includeAll?"":" and (gi.startDate is null or gi.startDate<=NOW()) and (gi.expiryDate is null or gi.expiryDate>=NOW())")).setString(0,
				groupCode).setString(1, interestType).list();
	}
}
