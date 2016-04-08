/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.RegisterInterest;

/**
 * @author mevan.d.souza
 *
 */
public interface RegisterInterestRepository extends BaseJpaRepository<RegisterInterest, Long> {

	public List<RegisterInterest> findByGroupCode(String groupCode, boolean includeAll); 
	
}
