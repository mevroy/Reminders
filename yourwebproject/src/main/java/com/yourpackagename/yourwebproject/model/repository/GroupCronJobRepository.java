/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupCronJob;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupCronJobRepository extends BaseJpaRepository<GroupCronJob, Long> {

	public List<GroupCronJob> findGroupCronJobsByGroupCode(String groupCode);
	public List<GroupCronJob> findGroupCronJobs();
	public GroupCronJob findByJobCode(String jobCode);
}
