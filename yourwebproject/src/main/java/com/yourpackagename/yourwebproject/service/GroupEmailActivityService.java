/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEmailActivityService extends BaseService<GroupEmailActivity, Long> {

	public List<GroupEmailActivity> findEmailActivitiesByEmailId(String groupEmailId);

}
