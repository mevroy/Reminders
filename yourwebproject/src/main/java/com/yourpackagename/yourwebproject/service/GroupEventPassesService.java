/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEventPassesService extends BaseService<GroupEventPass, String> {

	public List<GroupEventPass> findByGroupCodeAndGroupEventCode(String groupCode, String groupEventCode, boolean includeAll);
	public List<GroupEventPass> findByGroupEventInvite(GroupEventInvite groupEventInvite);
	public List<GroupEventPass> findApprovedPassesByGroupEventInvite(
			GroupEventInvite groupEventInvite);
	public List<GroupEventPass> findByTransaction(GroupEventPaymentTransaction groupEventPaymentTransaction);
	public List<GroupEventPass> findByGroupMember(GroupMember groupMember);
	public List<GroupEventPass> findByGroupMemberAndGroupEventCode(GroupMember groupMember, String groupEventCode);
	public GroupEventPass findByPassIdentifier(String passIdentifier);
	public GroupEventPass findByPassBarcode(String passBarcode);
	public List<GroupEventPass> findSoldTicketsByGroupCodeAndGroupEventCode(String groupCode, String groupEventCode);
	public List<GroupEventPass> findUnSoldTicketsByGroupCodeAndGroupEventCode(String groupCode, String groupEventCode);
	public List<GroupEventPass> findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(String groupCode, String groupEventCode, GroupEventPassCategory groupEventPassCategory);
	public List<GroupEventPass> findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(String groupCode, String groupEventCode, GroupEventPassCategory groupEventPassCategory);
	public List<GroupEventPass> assignPassesToTransaction(GroupEventPaymentTransaction groupEventPaymentTransaction, List<GroupEventPassCategory> groupEventPassCategories);
	public GroupEventPass releaseGroupEventPass(GroupEventPass pass);
	public List<GroupEventPass> createNewPasses(GroupEventPassCategory groupEventPassCategory, int numberOfPasses);
	public boolean checkAndReturnAvailability(GroupEventPassCategory groupEventPassCategory, int requiredNumberOfPasses);
	public int checkAndReturnPassAvailability(GroupEventPassCategory groupEventPassCategory, int requiredNumberOfPasses);
	public int checkPassAttendance(String groupEventPassCategoryId);
	public int checkTotalEventAttendance(String groupEventCode);
}
