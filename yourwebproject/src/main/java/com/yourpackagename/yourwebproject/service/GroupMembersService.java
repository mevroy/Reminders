/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupMembersService extends BaseService<GroupMember, String> {

	public List<GroupMember> findByGroupCodeAndMemberCategoryCode(String groupCode, String membercategoryCode);
	
	public List<GroupMember> findByGroupCode(String groupCode);
	
	public List<GroupMember> executeGenericQuery(String hibernateQuery);
	
	public List<List<GroupMember>> executeGenericQueryAsList(String hibernateQuery);
	
	public List<GroupMember> findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(String groupCode, String membercategoryCode, String groupEventCode);
	
	public List<GroupMember> findByGroupCodeNotExitingInGroupEventInvite(String groupCode, String groupEventCode);
	
	public GroupMember findbyMembershipIdentifier(String membershipIdentifier);
	
	public List<GroupMember> findByAssociatedEmailForGroupMember(String emailAddress, String groupCode);
	
	public ByteArrayOutputStream prefillPDF(GroupMember groupMember) throws IOException;
	
	public ByteArrayOutputStream prefillDefinedPDF(GroupMember groupMember, String httpPath) throws IOException;
	
	public  ByteArrayOutputStream prefillPDFTest(GroupMember groupMember) throws IOException;
	
	public ByteArrayOutputStream prefillPDFTest1(GroupMember groupMember) throws IOException ;
	
}
