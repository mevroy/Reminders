/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.AuditLog;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.repository.FeedbackRepository;
import com.yourpackagename.yourwebproject.model.repository.LoggerRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class LoggerRepositoryImpl extends BaseHibernateJpaRepository<AuditLog, Long> implements LoggerRepository{

	public List<AuditLog> findByGroupCode(String groupCode) {
		return (List<AuditLog>)sessionFactory.getCurrentSession().createQuery("from AuditLog a where a.groupCode = ? or a.groupCode = null order by accessDate desc").setString(0,
				groupCode).list();
	}


	public List<AuditLog> findByUser(User user, String groupCode) {
		return (List<AuditLog>)sessionFactory.getCurrentSession().createQuery("from AuditLog a where a.user = ? or a.groupCode = null order by accessDate desc").setParameter(0,
				user).list();
	}


}
