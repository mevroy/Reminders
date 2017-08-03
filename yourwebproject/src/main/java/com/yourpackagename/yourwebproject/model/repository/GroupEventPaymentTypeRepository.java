/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentType;


/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventPaymentTypeRepository extends BaseJpaRepository<GroupEventPaymentType, Long> {

	public List<GroupEventPaymentType> findByGroup(
			String groupCode, boolean includeExpired);
}
