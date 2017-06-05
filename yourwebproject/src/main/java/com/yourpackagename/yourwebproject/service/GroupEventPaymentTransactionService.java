/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;


/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventPaymentTransactionService extends BaseService<GroupEventPaymentTransaction, String> {

	public List<GroupEventPaymentTransaction> findByGroupEventInvite(GroupEventInvite groupEventInvite, boolean includeExpired);
	public List<GroupEventPaymentTransaction> findByUserCode(String userCode);
	public List<GroupEventPaymentTransaction> findByGroupEventCode(String groupEventCode);
	public List<GroupEventPaymentTransaction> findByCategoryCodeAndGroupEventCode(String memberCategoryCode, String groupEventCode);
//	public GroupEventPaymentTransaction processGroupEventPaymentTransaction(GroupEventPaymentTransaction groupEventPaymentTransaction);
	public GroupEventPaymentTransaction expirePaymentTransaction(GroupEventPaymentTransaction groupEventPaymentTransaction, boolean ignoreReferenceNumber, String emailingTemplate);
	public GroupEventPaymentTransaction preProcessExpiringPaymentTransaction(GroupEventPaymentTransaction groupEventPaymentTransaction, boolean ignoreReferenceNumber, String emailingTemplate);
	public GroupEventPaymentTransaction approvePaymentTransaction(GroupEventPaymentTransaction groupEventPaymentTransaction, String emailingTemplate);
//	public GroupEventPaymentTransaction cancelPaymentTransaction(GroupEventPaymentTransaction groupEventPaymentTransaction, String emailingTemplate);
}

