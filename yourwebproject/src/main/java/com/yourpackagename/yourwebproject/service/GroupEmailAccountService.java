/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEmailAccountService extends BaseService<GroupEmailAccount, Long> {

	public GroupEmailAccount findByEmailAccountCode(String emailAccountCode);
	public List<GroupEmailAccount> findByGroupCode(String groupCode);

}
