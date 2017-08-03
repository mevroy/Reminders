/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;

/**
 * @author mevan.d.souza
 *
 */
public interface GroupEmailActivityRepository extends BaseJpaRepository<GroupEmailActivity, Long> {

	public List<GroupEmailActivity> findEmailActivitiesByEmailId(String groupEmailId);

}
