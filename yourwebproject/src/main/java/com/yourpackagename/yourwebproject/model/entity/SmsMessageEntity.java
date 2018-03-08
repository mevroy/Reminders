/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author mevan.d.souza
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsMessageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7416809704140333799L;
	/**
	 * 
	 */
	private String to;
	private String body;
	private String from;
	private String messageId;
	private String notifyURL;
	private boolean replyRequest;

	public SmsMessageEntity()
	{
		//Dummy constructor to make sure the JSON serialization works
	}
	public SmsMessageEntity(String mobilePhone, String content, String from, String notifyURL) {
		this.to = mobilePhone;
		this.body = content;
		this.from = from;
		this.notifyURL = notifyURL;
	}



	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}



	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}



	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}



	public String getFrom() {
		return from;
	}



	public void setFrom(String from) {
		this.from = from;
	}

	
	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}



	/**
	 * @param to the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
	/**
	 * @return the notifyURL
	 */
	public String getNotifyURL() {
		return notifyURL;
	}



	/**
	 * @param to the notifyURL to set
	 */
	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}
	
	
	/**
	 * @return the replyRequest
	 */
	public boolean isReplyRequest() {
		return replyRequest;
	}



	/**
	 * @param to the replyRequest to set
	 */
	public void setReplyRequest(boolean replyRequest) {
		this.replyRequest = replyRequest;
	}
	
}
