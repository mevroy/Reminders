/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.PaymentStatus;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassesRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupEventPassesRepositoryImpl extends
		BaseHibernateJpaRepository<GroupEventPass, String> implements
		GroupEventPassesRepository {

	public List<GroupEventPass> findByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode, boolean includeAll) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupCode = ? and gep.groupEventCode = ? "
								+ (includeAll ? ""
										: " and (gep.passStartDate is null or gep.passStartDate<=NOW()) and (gep.passExpiryDate is null or gep.passExpiryDate>=NOW())"))
				.setString(0, groupCode).setString(1, groupEventCode).list();
	}

	public List<GroupEventPass> findByGroupEventInvite(
			GroupEventInvite groupEventInvite) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupEventInvite = :groupEventInvite ")
				.setParameter("groupEventInvite", groupEventInvite).list();
		// return(List<GroupEventPass>)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep1 where gep1.groupEventInvite = :groupEventInvite and gep1.groupEventPaymentTransaction is null union GroupEventPass gep2 where gep2.groupEventInvite = :groupEventInvite and gep2.groupEventPaymentTransaction.paymentStatus = 'APPROVED'").setParameter("groupEventInvite",
		// groupEventInvite).list();
	}

	public List<GroupEventPass> findApprovedPassesByGroupEventInvite(
			GroupEventInvite groupEventInvite) {
		// return(List<GroupEventPass>)sessionFactory.getCurrentSession().createQuery("from GroupEventPass gep where gep.groupEventInvite = :groupEventInvite ").setParameter("groupEventInvite",
		// groupEventInvite).list();
		List<GroupEventPass> finalList = new ArrayList<GroupEventPass>();
		finalList
				.addAll((List<GroupEventPass>) sessionFactory
						.getCurrentSession()
						.createQuery(
								"from GroupEventPass gep1 where gep1.groupEventInvite = :groupEventInvite and gep1.groupEventPaymentTransaction is null")
						.setParameter("groupEventInvite", groupEventInvite)
						.list());
		finalList
				.addAll((List<GroupEventPass>) sessionFactory
						.getCurrentSession()
						.createQuery(
								"from GroupEventPass gep2 where gep2.groupEventInvite = :groupEventInvite and gep2.groupEventPaymentTransaction.paymentStatus IN ('"
										+ PaymentStatus.APPROVED
										+ "','"
										+ PaymentStatus.PROCESSED + "')")
						.setParameter("groupEventInvite", groupEventInvite)
						.list());
		return finalList;
	}

	public List<GroupEventPass> findByGroupMember(GroupMember groupMember) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupMember = :groupMember ")
				.setParameter("groupMember", groupMember).list();
	}

	public GroupEventPass findByPassIdentifier(String passIdentifier) {
		return (GroupEventPass) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.passIdentifier = :passIdentifier ")
				.setParameter("passIdentifier", passIdentifier).uniqueResult();
	}

	public GroupEventPass findByPassBarcode(String passBarcode) {
		return (GroupEventPass) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.passBarcode = :passBarcode ")
				.setParameter("passBarcode", passBarcode).uniqueResult();
	}

	public List<GroupEventPass> findByGroupMemberAndGroupEventCode(
			GroupMember groupMember, String groupEventCode) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupMember = :groupMember and gep.groupEventCode = :groupEventCode")
				.setParameter("groupMember", groupMember)
				.setParameter("groupEventCode", groupEventCode).list();
	}

	public List<GroupEventPass> findSoldTicketsByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupCode = ? and gep.groupEventCode = ? and (gep.groupEventInvite is not null or gep.sold = 1)")
				.setString(0, groupCode).setString(1, groupEventCode).list();
	}

	public List<GroupEventPass> findUnSoldTicketsByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupCode = ? and gep.groupEventCode = ? and (gep.groupEventInvite is null and gep.groupMember is null and gep.sold = 0) order by gep.passBarcode asc")
				.setString(0, groupCode).setString(1, groupEventCode).list();
	}

	public List<GroupEventPass> findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
			String groupCode, String groupEventCode,
			GroupEventPassCategory groupEventPassCategory) {

		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupCode = :groupCode and gep.groupEventCode = :groupEventCode and (gep.groupEventPassCategory = :groupEventPassCategory or gep.groupEventPassCategory is null) and (gep.groupEventInvite is null and gep.groupMember is null and gep.sold = 0) order by gep.passBarcode asc")
				.setParameter("groupCode", groupCode)
				.setParameter("groupEventCode", groupEventCode)
				.setParameter("groupEventPassCategory", groupEventPassCategory)
				.list();
	}

	public List<GroupEventPass> findByTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupEventPaymentTransaction = :groupEventPaymentTransaction ")
				.setParameter("groupEventPaymentTransaction",
						groupEventPaymentTransaction).list();
	}

	public List<GroupEventPass> findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
			String groupCode, String groupEventCode,
			GroupEventPassCategory groupEventPassCategory) {
		return (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where gep.groupCode = :groupCode and gep.groupEventCode = :groupEventCode and (gep.groupEventPassCategory = :groupEventPassCategory) and (gep.groupEventInvite is not null or gep.sold = 1) order by gep.passBarcode asc")
				.setParameter("groupCode", groupCode)
				.setParameter("groupEventCode", groupEventCode)
				.setParameter("groupEventPassCategory", groupEventPassCategory)
				.list();

	}

	public int checkPassAttendance(String groupEventPassCategoryId) {

		List<GroupEventPass> attendedPasses = (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where  (gep.groupEventPassCategory.groupEventPassCategoryId = :groupEventPassCategoryId) and (gep.trackingDate is not null) ")
				.setParameter("groupEventPassCategoryId",
						groupEventPassCategoryId).list();
		if (CollectionUtils.isNotEmpty(attendedPasses))
			return attendedPasses.size();
		return 0;
	}

	public int checkTotalEventAttendance(String groupEventCode) {
		List<GroupEventPass> attendedPasses = (List<GroupEventPass>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from GroupEventPass gep where  (gep.groupEventCode = :groupEventCode) and (gep.trackingDate is not null) ")
				.setParameter("groupEventCode",
						groupEventCode).list();
		if (CollectionUtils.isNotEmpty(attendedPasses))
			return attendedPasses.size();
		return 0;
	}

}
