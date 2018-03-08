/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yourpackagename.yourwebproject.model.entity.enums.SmsStatus;

/**
 * @author mevan.d.souza
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsApiResponseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1223277032956616268L;
	/**
	 * 
	 */
	private String access_token;
	private String expires_in;
	private String token_type;
	
	private String messageId;
	//private SmsStatus status;
	private Date receivedTimestamp;
	private Date sentTimestamp;
	private Date acknowledgedTimestamp;
	private String from;
	private String content;
	
	private String body;
	
	private int status;
	private String message;
	private SmsMessageEntity[] messages;




	/**
	 * @return the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}


	/**
	 * @param access_token the access_token to set
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}


	/**
	 * @return the expires_in
	 */
	public String getExpires_in() {
		return expires_in;
	}


	/**
	 * @param expires_in the expires_in to set
	 */
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}


	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}


	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}



	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}


	/**
	 * @return the receivedTimestamp
	 */
	public Date getReceivedTimestamp() {
		return receivedTimestamp;
	}


	/**
	 * @param receivedTimestamp the receivedTimestamp to set
	 */
	public void setReceivedTimestamp(Date receivedTimestamp) {
		this.receivedTimestamp = receivedTimestamp;
	}


	/**
	 * @return the sentTimestamp
	 */
	public Date getSentTimestamp() {
		return sentTimestamp;
	}


	/**
	 * @param sentTimestamp the sentTimestamp to set
	 */
	public void setSentTimestamp(Date sentTimestamp) {
		this.sentTimestamp = sentTimestamp;
	}


	/**
	 * @return the acknowledgedTimestamp
	 */
	public Date getAcknowledgedTimestamp() {
		return acknowledgedTimestamp;
	}


	/**
	 * @param acknowledgedTimestamp the acknowledgedTimestamp to set
	 */
	public void setAcknowledgedTimestamp(Date acknowledgedTimestamp) {
		this.acknowledgedTimestamp = acknowledgedTimestamp;
	}


	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}


	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}


	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}


	public String getToken_type() {
		return token_type;
	}


	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	public SmsMessageEntity[]  getMessages() {
		return messages;
	}


	public void setMessages(SmsMessageEntity[] messages) {
		this.messages = messages;
	}

	
	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}
	
	public String toString() {
		return "Token:" + access_token + " , body:" + body +" , messageId:" + messageId + " ,  Status:" + status+" , acknowledgedTimestamp: "+acknowledgedTimestamp+" , sentTimestamp:"+sentTimestamp+" , receivedTimestamp:"+receivedTimestamp+" , content:"+content;
	}

}
