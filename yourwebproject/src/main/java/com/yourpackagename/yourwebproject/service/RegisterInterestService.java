/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.RegisterInterest;

/**
 * @author mevan.d.souza
 *
 */
public interface RegisterInterestService extends BaseService<RegisterInterest, Long>{
	public List<RegisterInterest> findByGroupCode(String groupCode, boolean includeAll);
}
