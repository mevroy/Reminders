/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupContent;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupInboundSMS;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.repository.FeedbackRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupContentRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupInboundSMSRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupSMSRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupInboundSMSRepositoryImpl extends BaseHibernateJpaRepository<GroupInboundSMS, String> implements GroupInboundSMSRepository{


	public List<GroupInboundSMS> findByGroupCode(String groupCode) {
		return (List<GroupInboundSMS>)sessionFactory.getCurrentSession().createQuery("from GroupInboundSMS gsms where gsms.groupCode = ? group by gsms.providerMessageId").setString(0,
                groupCode).list();
	}

	public List<GroupInboundSMS> findByMessageId(String messageId) {
		return (List<GroupInboundSMS>)sessionFactory.getCurrentSession().createQuery("from GroupInboundSMS gsms where gsms.providerMessageId = ? ").setString(0,
				messageId).list();
	}

	

}
