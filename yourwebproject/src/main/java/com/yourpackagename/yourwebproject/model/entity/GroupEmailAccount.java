/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_email_accounts")
public class GroupEmailAccount extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2593223243278529649L;

	@Column
	@Length(min = 3, max = 3)
	@NotNull
	@NotEmpty
	private String groupCode;
	
	@Column(unique = true)
	@NotNull
	@NotEmpty
	private String emailAccountCode;
	
	@Column
	@NotNull
	@NotEmpty
	private String protocol;
	
	@Column
	@NotNull
	@NotEmpty
	private String host;
	
	@Column
	@NotNull
	@NotEmpty
	private String username;
	
	@Column
	@NotNull
	@NotEmpty
	private String password;
	
	@Column
	private int port;
	
	@Column
	private boolean loginFailed;

	@Column
	private boolean holdEmailsOut;
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
	 * @return the emailAccountCode
	 */
	public String getEmailAccountCode() {
		return emailAccountCode;
	}

	/**
	 * @param emailAccountCode the emailAccountCode to set
	 */
	public void setEmailAccountCode(String emailAccountCode) {
		this.emailAccountCode = emailAccountCode;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the loginFailed
	 */
	public boolean isLoginFailed() {
		return loginFailed;
	}

	/**
	 * @param loginFailed the loginFailed to set
	 */
	public void setLoginFailed(boolean loginFailed) {
		this.loginFailed = loginFailed;
	}

	/**
	 * @return the holdEmailsOut
	 */
	public boolean isHoldEmailsOut() {
		return holdEmailsOut;
	}

	/**
	 * @param holdEmailsOut the holdEmailsOut to set
	 */
	public void setHoldEmailsOut(boolean holdEmailsOut) {
		this.holdEmailsOut = holdEmailsOut;
	}
	
	
}
