/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;






import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupMembersRepository extends BaseJpaRepository<GroupMember,String> {

	public List<GroupMember> findByGroupCodeAndMemberCategoryCode(String groupCode, String membercategoryCode);

	public List<GroupMember> findByGroupCode(String groupCode);
	
	public List<GroupMember> executeGenericQuery(String hibernateQuery);
	
	public List<GroupMember> findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(String groupCode, String membercategoryCode, String groupEventCode);
	
	public List<GroupMember> findByGroupCodeNotExitingInGroupEventInvite(String groupCode, String groupEventCode);
	
	public GroupMember findbyMembershipIdentifier(String membershipIdentifier);
	
	public List<GroupMember> findByAssociatedEmailForGroupMember(String emailAddress, String groupCode);
	
}