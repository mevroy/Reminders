/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.GroupCronJob;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupCronJobService extends BaseService<GroupCronJob, Long> {

	public List<GroupCronJob> findGroupCronJobsByGroupCode(String groupCode);
	public List<GroupCronJob> findGroupCronJobs();
	public GroupCronJob findByJobCode(String jobCode);
	
}
