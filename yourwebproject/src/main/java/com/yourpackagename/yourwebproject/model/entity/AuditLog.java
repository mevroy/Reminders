/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.framework.data.NoIDEntity;
import com.yourpackagename.yourwebproject.common.Key;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "audit_log")
public class AuditLog extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6245281231884970567L;

	/**
	 * 
	 */

	@Column
	@NotNull
	private String requestURI;

	@Column
	private String requestURL;

	@Column
	private String requestParams;

	@Column
	private String groupCode; // Need to define different groups Like MKC Board,
								// MKC Members, general interested members
	@Column
	private Date accessDate;

	@Column
	private boolean error;

	@Column
	private String errorTrace;

	@Column
	private String requestIp;

	@Column
	private String sessionId;

	@Column
	private String method;

	@Column
	private String userAgent;
	
	@Column
	private long executionTimeMillis;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private String clazz;

	public AuditLog() {

	}

	public AuditLog(HttpServletRequest request) {
		if (request != null) {
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			String groupCode = (String) request.getSession().getAttribute(
					Key.groupCode);
			this.setGroupCode(groupCode);
			this.setAccessDate(Calendar.getInstance().getTime());
			this.setCreatedAt(Calendar.getInstance().getTime());
			this.setMethod(request.getMethod());
			this.setRequestIp(ipAddress);
			this.setRequestParams((StringUtils.isNotBlank((request
					.getQueryString())) && request.getQueryString().trim()
					.length() > 250) ? request.getQueryString().substring(0,
					250) : request.getQueryString());
			this.setRequestURI(request.getRequestURI());
			this.setRequestURL(request.getRequestURL().toString());
			this.setSessionId(request.getSession().getId());
			this.setUpdatedAt(Calendar.getInstance().getTime());
			this.setUser((User) request.getSession().getAttribute(Key.user));
			this.setUserAgent(request.getHeader("user-agent"));
		}
	}

	/**
	 * @return the clazz
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the requestURI
	 */
	public String getRequestURI() {
		return requestURI;
	}

	/**
	 * @param requestURI
	 *            the requestURI to set
	 */
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	/**
	 * @return the requestURL
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param requestURL
	 *            the requestURL to set
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	/**
	 * @return the requestParams
	 */
	public String getRequestParams() {
		return requestParams;
	}

	/**
	 * @param requestParams
	 *            the requestParams to set
	 */
	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the accessDate
	 */
	public Date getAccessDate() {
		return accessDate;
	}

	/**
	 * @param accessDate
	 *            the accessDate to set
	 */
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * @return the requestIp
	 */
	public String getRequestIp() {
		return requestIp;
	}

	/**
	 * @param requestIp
	 *            the requestIp to set
	 */
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent
	 *            the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the errorTrace
	 */
	public String getErrorTrace() {
		return errorTrace;
	}

	/**
	 * @param errorTrace
	 *            the errorTrace to set
	 */
	public void setErrorTrace(String errorTrace) {
		this.errorTrace = errorTrace;
	}

	/**
	 * @return the executionTimeMillis
	 */
	public long getExecutionTimeMillis() {
		return executionTimeMillis;
	}

	/**
	 * @param executionTimeMillis the executionTimeMillis to set
	 */
	public void setExecutionTimeMillis(long executionTimeMillis) {
		this.executionTimeMillis = executionTimeMillis;
	}

}
