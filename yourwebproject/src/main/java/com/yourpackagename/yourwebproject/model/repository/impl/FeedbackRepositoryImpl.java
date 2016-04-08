/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.FeedbackRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class FeedbackRepositoryImpl extends BaseHibernateJpaRepository<Feedback, Long> implements FeedbackRepository{

	public List<Feedback> findByGroupEventCode(String groupEventCode) {
		return (List<Feedback>)sessionFactory.getCurrentSession().createQuery("from Feedback f where f.groupEventCode = ?").setString(0,
                groupEventCode).list();
	}

	public Feedback findByGroupEventInvite(GroupEventInvite groupEventInvite) {
		return (Feedback)sessionFactory.getCurrentSession().createQuery("from Feedback f where f.groupEventInviteId = :groupEventInviteId").setParameter("groupEventInviteId", groupEventInvite.getGroupEventInviteId()).uniqueResult();
	}

	public List<Feedback> findByGroupMember(GroupMember groupMember) {
		return (List<Feedback>)sessionFactory.getCurrentSession().createQuery("from Feedback f where f.serialNumber = ?").setString(0,
				groupMember.getSerialNumber()).list();
	}

	public List<Feedback> findByMemberCategoryCodeAndGroupEventCode(
			String memberCategoryCode, String groupEventCode) {
		return (List<Feedback>)sessionFactory.getCurrentSession().createQuery("select f from Feedback f, GroupMember gm where f.groupEventCode = ? and f.serialNumber=gm.serialNumber and gm.memberCategoryCode = ?").setString(0,
                groupEventCode).setParameter(1, memberCategoryCode).list();
	}

}
