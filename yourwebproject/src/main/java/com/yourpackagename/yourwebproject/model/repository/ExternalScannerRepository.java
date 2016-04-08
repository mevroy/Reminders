/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository;

import java.util.List;

import com.yourpackagename.framework.data.BaseJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.ExternalScanner;


/**
 * @author mevan.d.souza
 *
 */
public interface ExternalScannerRepository extends BaseJpaRepository<ExternalScanner, Long> {

	public ExternalScanner findBydeviceUuid(String groupCode, String deviceUuid);
	public List<ExternalScanner> findByGroupCode(String groupCode);
}
