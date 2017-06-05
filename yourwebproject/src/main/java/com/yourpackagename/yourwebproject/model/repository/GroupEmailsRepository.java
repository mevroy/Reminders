/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEmailsRepository extends BaseJpaRepository<GroupEmail, String> {

	public List<GroupEmail> findEmailsToBeSent(String conversionToTimeZone);
	public List<GroupEmail> findEmailsByGroupCode(String groupCode);
	public List<GroupEmail> findEmailsByGroupEventCode(String groupEventCode);
	public List<GroupEmail> findEmailsByGroupEventCodeAndSerialNumber(String groupEventCode, String serialNumber);
	public List<GroupEmail> findUnassignedEmailsByGroupCode(String groupCode);
	public List<GroupEmail> findEmailsByMemberCategoryCodeAndGroupEventCode(String memberCategoryCode, String groupEventCode);
}
