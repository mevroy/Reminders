/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassesRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupEventPassesRepositoryImpl extends
		BaseHibernateJpaRepository<GroupEventPass, String> implements GroupEventPassesRepository{

	public List<GroupEventPass> findByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode, boolean includeAll) {
		return(List<GroupEventPass>)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep where gep.groupCode = ? and gep.groupEventCode = ? "+(includeAll?"":" and (gep.passStartDate is null or gep.passStartDate<=CURDATE()) and (gep.passExpiryDate is null or gep.passExpiryDate>=CURDATE())")).setString(0,
				groupCode).setString(1, groupEventCode).list();
	}

	public List<GroupEventPass> findByGroupEventInvite(
			GroupEventInvite groupEventInvite) {
		return(List<GroupEventPass>)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep where gep.groupEventInvite = :groupEventInvite ").setParameter("groupEventInvite", groupEventInvite).list();
	}

	public List<GroupEventPass> findByGroupMember(GroupMember groupMember) {
		return(List<GroupEventPass>)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep where gep.groupMember = :groupMember ").setParameter("groupMember", groupMember).list();
	}

	public GroupEventPass findByPassIdentifier(String passIdentifier) {
		return(GroupEventPass)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep where gep.passIdentifier = :passIdentifier ").setParameter("passIdentifier", passIdentifier).uniqueResult();
	}

	public GroupEventPass findByPassBarcode(String passBarcode) {
		return(GroupEventPass)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep where gep.passBarcode = :passBarcode ").setParameter("passBarcode", passBarcode).uniqueResult();
	}

	public List<GroupEventPass> findByGroupMemberAndGroupEventCode(
			GroupMember groupMember, String groupEventCode) {
		return(List<GroupEventPass>)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep where gep.groupMember = :groupMember and gep.groupEventCode = :groupEventCode").setParameter("groupMember", groupMember).setParameter("groupEventCode", groupEventCode).list();
	}

}
