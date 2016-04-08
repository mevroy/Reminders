/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupContent;


/**
 * @author mevan.d.souza
 *
 */
public interface GroupContentService extends BaseService<GroupContent, String> {

	public List<GroupContent> findByGroupCode(String groupCode, boolean includeExpired);
}
