/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventPassCategoryService extends BaseService<GroupEventPassCategory, String> {

	public List<GroupEventPassCategory> findByGroupCodeAndGroupEventCode(String groupCode, String groupEventCode, boolean includeNotAvailableForPurchase);
	public List<GroupEventPassCategory> findByGroupCodeAndGroupEventCodeForMemberType(String groupCode, String groupEventCode, boolean includeInactive, boolean availableForPurchase, String[] isMember);
	
}
