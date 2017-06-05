/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentType;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPaymentTransactionRepository;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventPaymentTypeService extends BaseService<GroupEventPaymentType	, Long> {

	public List<GroupEventPaymentType> findByGroup(
			String groupCode, boolean includeExpired);
	
}
