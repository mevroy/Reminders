/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mevan.d.souza
 *
 */

public class SendGridEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7416809704140333799L;
	/**
	 * 
	 */
	private String email;
	private Date timestamp;
	private String event;
	private String useragent;
	private String ip;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the useragent
	 */
	public String getUseragent() {
		return useragent;
	}

	/**
	 * @param useragent
	 *            the useragent to set
	 */
	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String toString() {
		return "Email:" + email + " , Event Type:" + event + " , Time:"
				+ timestamp + " , User Agent:" + useragent + " , IP:" + ip;
	}

}
