/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.framework.validation.Validity;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.PaymentStatus;
import com.yourpackagename.yourwebproject.model.repository.FeedbackRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPaymentTransactionRepository;
import com.yourpackagename.yourwebproject.service.FeedbackService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventPaymentTransactionService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventPaymentTransactionServiceImpl extends
		BaseJpaServiceImpl<GroupEventPaymentTransaction, String> implements
		GroupEventPaymentTransactionService {

	private @Autowired GroupEventPaymentTransactionRepository groupEventPaymentTransactionRepository;
	private @Autowired GroupEventPassesService groupEventPassesService;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupEmailsService groupEmailsService;
	private @Autowired GroupEventInviteRSVPService groupEventInviteRSVPService;
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupEventInviteService groupEventInviteService;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventPaymentTransactionRepository;
		this.entityClass = GroupEventPaymentTransaction.class;
		this.baseJpaRepository
				.setupEntityClass(GroupEventPaymentTransaction.class);

	}

	public List<GroupEventPaymentTransaction> findByGroupEventInvite(
			GroupEventInvite groupEventInvite, boolean includeExpired) {
		return groupEventPaymentTransactionRepository.findByGroupEventInvite(
				groupEventInvite, includeExpired);
	}

	public List<GroupEventPaymentTransaction> findByUserCode(String userCode) {
		return groupEventPaymentTransactionRepository.findByUserCode(userCode);
	}

	public List<GroupEventPaymentTransaction> findByGroupEventCode(
			String groupEventCode) {
		return groupEventPaymentTransactionRepository
				.findByGroupEventCode(groupEventCode);
	}

	public List<GroupEventPaymentTransaction> findByCategoryCodeAndGroupEventCode(
			String memberCategoryCode, String groupEventCode) {

		return groupEventPaymentTransactionRepository
				.findByCategoryCodeAndGroupEventCode(memberCategoryCode,
						groupEventCode);
	}

	public GroupEventPaymentTransaction processGroupEventPaymentTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction) {
		// TODO Auto-generated method stub
		return null;
	}

	public GroupEventPaymentTransaction expirePaymentTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction,
			boolean ignoreReferenceNumber, String emailingTemplate) {

		if (groupEventPaymentTransaction == null
				|| PaymentStatus.EXPIRED.equals(groupEventPaymentTransaction
						.getPaymentStatus())) {
		} else {
			if (!groupEventPaymentTransaction.isTransactionApproved()
					&& ((groupEventPaymentTransaction
							.getTransactionExpiryDateTime() != null
							&& groupEventPaymentTransaction
									.getTransactionExpiryDateTime().before(
											Calendar.getInstance().getTime()) && (PaymentStatus.AWAITINGPMT
								.equals(groupEventPaymentTransaction
										.getPaymentStatus()))))) {
				if (ignoreReferenceNumber
						|| StringUtils.isBlank(groupEventPaymentTransaction
								.getUserReferenceNumber())) {


					List<GroupEventPass> groupPasses = groupEventPassesService
							.findByTransaction(groupEventPaymentTransaction);
					if (CollectionUtils.isNotEmpty(groupPasses)) {
						for (GroupEventPass pass : groupPasses) {
							pass =	groupEventPassesService.releaseGroupEventPass(pass);
						}
					}
					try {
						if (StringUtils.isNotBlank(emailingTemplate)) {
							GroupEmailTemplate get = groupEmailTemplateService
									.findbyTemplateName(emailingTemplate);
							Date correspondenceDate = null;
							if (get != null) {
								Map<String, Object> modelMap = new HashMap<String, Object>();
								modelMap.put("groupMember",
										groupEventPaymentTransaction
												.getGroupEventInvite()
												.getGroupMember());
								modelMap.put("groupEventInvite",
										groupEventInviteService.findById(groupEventPaymentTransaction.getGroupEventInvite().getGroupEventInviteId()));
								modelMap.put(
										"groupEvent",
										groupEventsService
												.findByGroupEventCode(groupEventPaymentTransaction
														.getGroupEventCode()));
								modelMap.put("groupEmailTemplate", get);
								List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
										.findByGroupEventInvite(groupEventPaymentTransaction
												.getGroupEventInvite());
								if (tempList != null && tempList.size() > 0) {
									modelMap.put("groupEventInviteRSVP",
											tempList.get(0));
								}
								modelMap.put("groupEventPaymentTransaction",
										groupEventPaymentTransaction);
								modelMap.put("groupEventPasses", groupPasses);
								GroupEmail groupEmail = new GroupEmail();
								groupEmail = groupEmailsService.createEmail(
										groupEmail, modelMap);
								correspondenceDate = Calendar.getInstance().getTime();

							}
							groupEventPaymentTransaction = this.findById(groupEventPaymentTransaction.getTransactionId());
							groupEventPaymentTransaction.setCorrespondenceDateTime(correspondenceDate);
							groupEventPaymentTransaction.setPaymentStatus(PaymentStatus.EXPIRED);
							groupEventPaymentTransaction.setUpdatedAt(Calendar.getInstance().getTime());
							groupEventPaymentTransaction = this.update(groupEventPaymentTransaction);
						}
						else 
						{
							groupEventPaymentTransaction = this.findById(groupEventPaymentTransaction.getTransactionId());
							groupEventPaymentTransaction.setPaymentStatus(PaymentStatus.EXPIRED);
							groupEventPaymentTransaction.setUpdatedAt(Calendar.getInstance().getTime());
							groupEventPaymentTransaction = this.update(groupEventPaymentTransaction);
						}

					} catch (Exception e) {

					}
				}
			}
		}

		return groupEventPaymentTransaction;

	}

	public GroupEventPaymentTransaction preProcessExpiringPaymentTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction,
			boolean ignoreReferenceNumber, String emailingTemplate) {
		if (groupEventPaymentTransaction == null
				|| PaymentStatus.EXPIRED.equals(groupEventPaymentTransaction
						.getPaymentStatus())) {
		} else {
			if (!groupEventPaymentTransaction.isTransactionApproved()
					&& ((groupEventPaymentTransaction
							.getTransactionExpiryDateTime() != null
							&& new DateTime(
									groupEventPaymentTransaction
											.getTransactionExpiryDateTime())
									.minusHours(24).isBeforeNow() && (PaymentStatus.AWAITINGPMT
								.equals(groupEventPaymentTransaction
										.getPaymentStatus())))) && (groupEventPaymentTransaction.getCorrespondenceDateTime()==null || new DateTime(
												groupEventPaymentTransaction
												.getCorrespondenceDateTime())
										.plusHours(25).isBeforeNow())) {

				try {
					if ((ignoreReferenceNumber || StringUtils
							.isBlank(groupEventPaymentTransaction
									.getUserReferenceNumber()))
							&& StringUtils.isNotBlank(emailingTemplate)) {
						GroupEmailTemplate get = groupEmailTemplateService
								.findbyTemplateName(emailingTemplate);
						if (get != null) {
							List<GroupEventPass> groupPasses = groupEventPassesService
									.findByTransaction(groupEventPaymentTransaction);

							Map<String, Object> modelMap = new HashMap<String, Object>();
							modelMap.put("groupMember",
									groupEventPaymentTransaction
											.getGroupEventInvite()
											.getGroupMember());
							modelMap.put("groupEventInvite",
									groupEventInviteService.findById(groupEventPaymentTransaction.getGroupEventInvite().getGroupEventInviteId()));
							modelMap.put(
									"groupEvent",
									groupEventsService
											.findByGroupEventCode(groupEventPaymentTransaction
													.getGroupEventCode()));
							modelMap.put("groupEmailTemplate", get);
							List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
									.findByGroupEventInvite(groupEventPaymentTransaction
											.getGroupEventInvite());
							if (tempList != null && tempList.size() > 0) {
								modelMap.put("groupEventInviteRSVP",
										tempList.get(0));
							}
							modelMap.put("groupEventPaymentTransaction",
									groupEventPaymentTransaction);
							modelMap.put("groupEventPasses", groupPasses);
							GroupEmail groupEmail = new GroupEmail();
							groupEmail = groupEmailsService.createEmail(
									groupEmail, modelMap);
							groupEventPaymentTransaction = this.findById(groupEventPaymentTransaction.getTransactionId());
							groupEventPaymentTransaction.setCorrespondenceDateTime(Calendar.getInstance().getTime());
							groupEventPaymentTransaction = this
									.update(groupEventPaymentTransaction);
						}
					}
				} catch (Exception e) {

				}
			}
		}

		return groupEventPaymentTransaction;

	}

	public GroupEventPaymentTransaction approvePaymentTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction,
			String emailingTemplate) {
		if (PaymentStatus.PROCESSED.equals(groupEventPaymentTransaction
				.getPaymentStatus())) {

		} else if (PaymentStatus.APPROVED.equals(groupEventPaymentTransaction
				.getPaymentStatus())) {
			try {
				GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
						.findbyTemplateName(emailingTemplate);
				if (gEmailTemplate != null) {
					Map<String, Object> modelMap = new HashMap<String, Object>();
					if (groupEventPaymentTransaction.getGroupEventInvite() != null) {
						modelMap.put("groupMember",
								groupEventPaymentTransaction
										.getGroupEventInvite().getGroupMember());
						modelMap.put("groupEventInvite",
								groupEventInviteService.findById(groupEventPaymentTransaction.getGroupEventInvite().getGroupEventInviteId()));
						List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
								.findByGroupEventInvite(groupEventPaymentTransaction
										.getGroupEventInvite());
						if (tempList != null && tempList.size() > 0) {
							modelMap.put("groupEventInviteRSVP",
									tempList.get(0));
						}
					}
					List<GroupEventPass> groupPasses = groupEventPassesService
							.findByTransaction(groupEventPaymentTransaction);
					modelMap.put("groupEventPasses", groupPasses);
					modelMap.put("groupEvent", groupEventsService
							.findByGroupEventCode(groupEventPaymentTransaction
									.getGroupEventCode()));
					modelMap.put("groupEmailTemplate", gEmailTemplate);
					modelMap.put("groupEventPaymentTransaction",
							groupEventPaymentTransaction);
					GroupEmail groupEmail = new GroupEmail();
					groupEmail = groupEmailsService.createEmail(groupEmail,
							modelMap);
					groupEventPaymentTransaction = this.findById(groupEventPaymentTransaction.getTransactionId());
					groupEventPaymentTransaction
							.setCorrespondenceDateTime(Calendar.getInstance()
									.getTime());
					groupEventPaymentTransaction.setUpdatedAt(Calendar
							.getInstance().getTime());
					groupEventPaymentTransaction
							.setPaymentStatus(PaymentStatus.PROCESSED);

					groupEventPaymentTransaction = this
							.update(groupEventPaymentTransaction);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return groupEventPaymentTransaction;
	}

	public GroupEventPaymentTransaction cancelPaymentTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction,
			String emailingTemplate) {
		// TODO Auto-generated method stub
		return null;
	}

}
