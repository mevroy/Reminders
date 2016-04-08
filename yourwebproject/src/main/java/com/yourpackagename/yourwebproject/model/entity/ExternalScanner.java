/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_external_scanner", uniqueConstraints=
@UniqueConstraint(columnNames={"groupCode", "deviceUuid"}))
public class ExternalScanner extends JpaEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4815348910941833591L;

	@Column
	private String groupCode;
	
	@Column
	private Date accessStartDate;
	
	@Column
	private Date accessEndDate;
	
	@Column
	private String deviceName;
	
	@Column
	private String deviceOwner;
	
	@Column
	private String deviceUuid;
	
	@Column
	private Date lastAccessDate;

	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the accessStartDate
	 */
	public Date getAccessStartDate() {
		return accessStartDate;
	}

	/**
	 * @param accessStartDate the accessStartDate to set
	 */
	public void setAccessStartDate(Date accessStartDate) {
		this.accessStartDate = accessStartDate;
	}

	/**
	 * @return the accessEndDate
	 */
	public Date getAccessEndDate() {
		return accessEndDate;
	}

	/**
	 * @param accessEndDate the accessEndDate to set
	 */
	public void setAccessEndDate(Date accessEndDate) {
		this.accessEndDate = accessEndDate;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the deviceOwner
	 */
	public String getDeviceOwner() {
		return deviceOwner;
	}

	/**
	 * @param deviceOwner the deviceOwner to set
	 */
	public void setDeviceOwner(String deviceOwner) {
		this.deviceOwner = deviceOwner;
	}

	/**
	 * @return the deviceUuid
	 */
	public String getDeviceUuid() {
		return deviceUuid;
	}

	/**
	 * @param deviceUuid the deviceUuid to set
	 */
	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	/**
	 * @return the lastAccessDate
	 */
	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	/**
	 * @param lastAccessDate the lastAccessDate to set
	 */
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
