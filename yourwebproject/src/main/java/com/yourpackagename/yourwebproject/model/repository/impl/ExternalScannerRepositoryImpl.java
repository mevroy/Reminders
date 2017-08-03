/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.ExternalScanner;
import com.yourpackagename.yourwebproject.model.repository.ExternalScannerRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class ExternalScannerRepositoryImpl extends BaseHibernateJpaRepository<ExternalScanner, Long> implements ExternalScannerRepository{

	public ExternalScanner findBydeviceUuid(String groupCode, String deviceUuid) {
		return (ExternalScanner)sessionFactory.getCurrentSession().createQuery("from ExternalScanner es where es.groupCode = ? and es.deviceUuid = ?").setString(0,
                groupCode).setString(1, deviceUuid).uniqueResult();
	}


	public List<ExternalScanner> findByGroupCode(String groupCode) {
		return (List<ExternalScanner>)sessionFactory.getCurrentSession().createQuery("from ExternalScanner es where es.groupCode = ? ").setString(0,
                groupCode).list();
	}

}
