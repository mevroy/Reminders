/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassCategoryRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassesRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupEventPassCategoryRepositoryImpl extends
		BaseHibernateJpaRepository<GroupEventPassCategory, String> implements GroupEventPassCategoryRepository{

	public List<GroupEventPassCategory> findByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode, boolean includeNotAvailableForPurchase) {
		return(List<GroupEventPassCategory>)sessionFactory.getCurrentSession().createQuery("from GroupEventPassCategory gpc where gpc.group.groupCode = ? and gpc.groupEvent.eventCode = ? "+(includeNotAvailableForPurchase?"":" and (gpc.purchaseStartDateTime is null or gpc.purchaseStartDateTime<=CURDATE()) and (gpc.purchaseExpiryDateTime is null or gpc.purchaseExpiryDateTime>=CURDATE())")).setString(0,
				groupCode).setString(1, groupEventCode).list();
	}


	public List<GroupEventPassCategory> findByGroupCodeAndGroupEventCodeForMemberType(
			String groupCode, String groupEventCode, boolean includeInactive, boolean availableForPurchase, String isMember) {
		return(List<GroupEventPassCategory>)sessionFactory.getCurrentSession().createQuery("from GroupEventPassCategory gpc where gpc.group.groupCode = ? and gpc.groupEvent.eventCode = ? and (gpc.memberOnlyPurchase = ? or gpc.memberOnlyPurchase is null) and gpc.disablePurchase = ? "+(includeInactive?"":" and (gpc.purchaseStartDateTime is null or gpc.purchaseStartDateTime<=CURDATE()) and (gpc.purchaseExpiryDateTime is null or gpc.purchaseExpiryDateTime>=CURDATE())")).setString(0,
				groupCode).setString(1, groupEventCode).setString(2, isMember).setBoolean(3, !availableForPurchase).list();
	}


}
