/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.repository.GroupEventsRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupEventsRepositoryImpl extends
		BaseHibernateJpaRepository<GroupEvents, Long> implements GroupEventsRepository {

	public List<GroupEvents> findByGroupCode(String groupCode) {
		Query q = sessionFactory.getCurrentSession().createQuery("from GroupEvents ge where ge.groupCode = ? and (ge.expiryDate is null or date(ge.expiryDate) >= CURDATE()) order by ge.eventDate desc").setString(0,
        		groupCode);
		List<GroupEvents> groupEvents = q.list();
        return  groupEvents;
	}

	public List<GroupEvents> findByGroupCodeAndMemberCategoryCode(
			String groupCode, String memberCategoryCode) {
		Query q = sessionFactory.getCurrentSession().createQuery("from GroupEvents ge where ge.groupCode = ? and (ge.memberCategoryCode = ? or ge.memberCategoryCode is null or ge.memberCategoryCode = '') and (ge.expiryDate is null or date(ge.expiryDate) >= CURDATE())  order by ge.eventDate desc").setString(0,
        		groupCode).setString(1, memberCategoryCode);
		List<GroupEvents> groupEvents = q.list();
        return  groupEvents;
	}

	public GroupEvents findByGroupEventCode(String groupEventCode) {
		Query q = sessionFactory.getCurrentSession().createQuery("from GroupEvents ge where ge.eventCode = ? order by ge.eventDate desc").setString(0,
				groupEventCode);
		GroupEvents groupEvent = (GroupEvents)q.uniqueResult();
        return  groupEvent;
	}

	public List<GroupEvents> findByGroupCode(String groupCode,
			boolean includeExpired) {
		Query q = sessionFactory.getCurrentSession().createQuery("from GroupEvents ge where ge.groupCode = ?  "+(includeExpired? "":" and (ge.expiryDate is null or date(ge.expiryDate) >= CURDATE())")+ " order by ge.eventDate desc").setString(0,
        		groupCode);
		List<GroupEvents> groupEvents = q.list();
        return  groupEvents;
	}

	public List<GroupEvents> findByGroupCodeAndMemberCategoryCode(
			String groupCode, String memberCategoryCode, boolean includeExpired) {
		Query q = sessionFactory.getCurrentSession().createQuery("from GroupEvents ge where ge.groupCode = ? and (ge.memberCategoryCode = ? or ge.memberCategoryCode is null or ge.memberCategoryCode = '') "+(includeExpired? "":" and (ge.expiryDate is null or date(ge.expiryDate) >= CURDATE())")+" order by ge.eventDate desc").setString(0,
        		groupCode).setString(1, memberCategoryCode);
		List<GroupEvents> groupEvents = q.list();
        return  groupEvents;
	}

}
