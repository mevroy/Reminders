
/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;
import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;

public interface GroupEventPaymentTransactionRepository extends BaseJpaRepository<GroupEventPaymentTransaction, String> {

	public List<GroupEventPaymentTransaction> findByGroupEventInvite(GroupEventInvite groupEventInvite, boolean includeExpired);
	public List<GroupEventPaymentTransaction> findByUserCode(String userCode);
	public List<GroupEventPaymentTransaction> findByGroupEventCode(String groupEventCode);
	public List<GroupEventPaymentTransaction> findByCategoryCodeAndGroupEventCode(String memberCategoryCode, String groupEventCode);
	
}