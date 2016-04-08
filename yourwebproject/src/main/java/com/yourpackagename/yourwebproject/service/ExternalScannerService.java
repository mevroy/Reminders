/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.util.List;

import com.yourpackagename.framework.data.BaseService;
import com.yourpackagename.yourwebproject.model.entity.ExternalScanner;
import com.yourpackagename.yourwebproject.model.entity.Feedback;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;

/**
 * @author mevan.d.souza
 *
 */
public interface ExternalScannerService extends BaseService<ExternalScanner, Long> {

	public ExternalScanner findBydeviceUuid(String groupCode, String deviceUuid);
	public List<ExternalScanner> findByGroupCode(String groupCode);
}
