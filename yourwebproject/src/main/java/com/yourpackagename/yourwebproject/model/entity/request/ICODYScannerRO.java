/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity.request;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author mevan.d.souza
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ICODYScannerRO {

	public String typeID;
	public LocationRO location;
	public String deviceName;
	//public String scandate;
	public String uuid;
	public String value;
	public List<HashMap<String, Object>> workflow;
	/**
	 * @return the typeID
	 */
	public String getTypeID() {
		return typeID;
	}
	/**
	 * @param typeID the typeID to set
	 */
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	/**
	 * @return the location
	 */
	public LocationRO getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationRO location) {
		this.location = location;
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
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the workflow
	 */
	public List<HashMap<String, Object>> getWorkflow() {
		return workflow;
	}
	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(List<HashMap<String, Object>> workflow) {
		this.workflow = workflow;
	}



	
	
}
