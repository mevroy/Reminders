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
@Table(name = "group_push_notification_accounts")
public class GroupPushNotificationAccount extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4529768372673698364L;

	

	@Column
	@Length(min = 3, max = 3)
	@NotNull
	@NotEmpty
	private String groupCode;
	
	@Column(unique = true)
	@NotNull
	@NotEmpty
	private String accountCode;
	
	@Column
	@NotNull
	@NotEmpty
	private String pushNotificationsTargetType;
	
	@Column
	@NotNull
	@NotEmpty
	private String appKey;
	
	@Column
	@NotNull
	@NotEmpty
	private String appSecret;
	
	@Column
	private String accountName;
	
	
	@Column
	private String subscriptionLink;
	

	@Column
	private String appAlias;
	
	
	@Column
	private boolean authorizationFailed;

	@Column
	private boolean holdPushNotifications;

	@Column
	private String pushNotificationQRImageLink;

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
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}


	/**
	 * @return the pushNotificationsTargetType
	 */
	public String getPushNotificationsTargetType() {
		return pushNotificationsTargetType;
	}

	/**
	 * @param pushNotificationsTargetType the pushNotificationsTargetType to set
	 */
	public void setPushNotificationsTargetType(String pushNotificationsTargetType) {
		this.pushNotificationsTargetType = pushNotificationsTargetType;
	}

	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/**
	 * @param appSecret the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the subscriptionLink
	 */
	public String getSubscriptionLink() {
		return subscriptionLink;
	}

	/**
	 * @param subscriptionLink the subscriptionLink to set
	 */
	public void setSubscriptionLink(String subscriptionLink) {
		this.subscriptionLink = subscriptionLink;
	}

	/**
	 * @return the appAlias
	 */
	public String getAppAlias() {
		return appAlias;
	}

	/**
	 * @param appAlias the appAlias to set
	 */
	public void setAppAlias(String appAlias) {
		this.appAlias = appAlias;
	}

	/**
	 * @return the authorizationFailed
	 */
	public boolean isAuthorizationFailed() {
		return authorizationFailed;
	}

	/**
	 * @param authorizationFailed the authorizationFailed to set
	 */
	public void setAuthorizationFailed(boolean authorizationFailed) {
		this.authorizationFailed = authorizationFailed;
	}

	/**
	 * @return the holdPushNotifications
	 */
	public boolean isHoldPushNotifications() {
		return holdPushNotifications;
	}

	/**
	 * @param holdPushNotifications the holdPushNotifications to set
	 */
	public void setHoldPushNotifications(boolean holdPushNotifications) {
		this.holdPushNotifications = holdPushNotifications;
	}

	/**
	 * @return the pushNotificationQRImageLink
	 */
	public String getPushNotificationQRImageLink() {
		return pushNotificationQRImageLink;
	}

	/**
	 * @param pushNotificationQRImageLink the pushNotificationQRImageLink to set
	 */
	public void setPushNotificationQRImageLink(String pushNotificationQRImageLink) {
		this.pushNotificationQRImageLink = pushNotificationQRImageLink;
	}

	
}
