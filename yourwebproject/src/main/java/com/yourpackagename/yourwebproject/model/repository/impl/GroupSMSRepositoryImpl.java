/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.repository.GroupSMSRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupSMSRepositoryImpl extends BaseHibernateJpaRepository<GroupSMS, String> implements GroupSMSRepository{


	public List<GroupSMS> findByGroupCode(String groupCode) {
		return (List<GroupSMS>)sessionFactory.getCurrentSession().createQuery("from GroupSMS gsms where gsms.groupCode = ? ").setString(0,
                groupCode).list();
	}

	public GroupSMS findByMessageId(String messageId) {
		return (GroupSMS)sessionFactory.getCurrentSession().createQuery("from GroupSMS gsms where gsms.providerMessageId = ? ").setString(0,
				messageId).uniqueResult();
	}

	public List<GroupSMS> findSMSByGroupEventCode(String groupEventCode) {
		return (List<GroupSMS>)sessionFactory.getCurrentSession().createQuery("select ge from GroupSMS ge, GroupEventInvite gei where  ge.groupEventInviteId = gei.groupEventInviteId and gei.groupEventCode = ?").setParameter(0, groupEventCode).list();
	}

	public List<GroupSMS> findSMSByMemberCategoryCodeAndGroupEventCode(
			String memberCategoryCode, String groupEventCode) {
		return (List<GroupSMS>)sessionFactory.getCurrentSession().createQuery("select ge from GroupSMS ge, GroupEventInvite gei where ge.groupMember.memberCategoryCode = ? and ge.groupEventInviteId = gei.groupEventInviteId and gei.groupEventCode = ? and ge.groupMember.memberCategoryCode = gei.memberCategoryCode").setParameter(0, memberCategoryCode).setParameter(1, groupEventCode).list();
	}

	public List<GroupSMS> findUnassignedSMSByGroupCode(String groupCode) {
		return (List<GroupSMS>)sessionFactory.getCurrentSession().createQuery("from GroupSMS ge where ge.groupCode = ?  and ( ge.groupEventInviteId = null or ge.groupEventInviteId = '')").setParameter(0, groupCode).list();
	}
	

}
