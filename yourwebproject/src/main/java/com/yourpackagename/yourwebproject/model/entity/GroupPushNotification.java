/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_push_notifications")
public class GroupPushNotification extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8747470786696259299L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "groupPushNotificationId", unique = true, updatable = false)
	private String groupPushNotificationId;
	
	@Column
	private String pushedId;
	
	
	@Column(columnDefinition="TEXT") @NotNull @NotEmpty
	private String content;
	
	/* Date when the Notification is to be sent */
	@Column
	private Date pushNotificationDate;
	
	/* Actual Date when Notification is sent */
	@Column
	private Date pushNotificationSentDate;
	
	/*Date after which Notification should not be sent*/
	@Column
	private Date pushNotificationExpirydate; 
	
	@Column
	private Date pushNotificationViewedDate;
	
	@Column
	private Date pushNotificationBounceDate;
	
	
	@Column
	private boolean pushNotificationHeld;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "accountCode")
	private GroupPushNotificationAccount groupPushNotificationAccount;
	
	@Column
	private String groupEventInviteId;
	
	@Column
	private String pushNotificationError;
	
	//app, channel, user
	@Column @NotNull @NotEmpty
	private String pushNotificationsTargetType;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "serialNumber")
	private GroupMember groupMember;

	@ManyToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupId")
	private Groups group;

	/**
	 * @return the groupPushNotificationId
	 */
	public String getGroupPushNotificationId() {
		return groupPushNotificationId;
	}

	/**
	 * @param groupPushNotificationId the groupPushNotificationId to set
	 */
	public void setGroupPushNotificationId(String groupPushNotificationId) {
		this.groupPushNotificationId = groupPushNotificationId;
	}

	/**
	 * @return the pushedId
	 */
	public String getPushedId() {
		return pushedId;
	}

	/**
	 * @param pushedId the pushedId to set
	 */
	public void setPushedId(String pushedId) {
		this.pushedId = pushedId;
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

	/**
	 * @return the pushNotificationDate
	 */
	public Date getPushNotificationDate() {
		return pushNotificationDate;
	}

	/**
	 * @param pushNotificationDate the pushNotificationDate to set
	 */
	public void setPushNotificationDate(Date pushNotificationDate) {
		this.pushNotificationDate = pushNotificationDate;
	}

	/**
	 * @return the pushNotificationSentDate
	 */
	public Date getPushNotificationSentDate() {
		return pushNotificationSentDate;
	}

	/**
	 * @param pushNotificationSentDate the pushNotificationSentDate to set
	 */
	public void setPushNotificationSentDate(Date pushNotificationSentDate) {
		this.pushNotificationSentDate = pushNotificationSentDate;
	}

	/**
	 * @return the pushNotificationExpirydate
	 */
	public Date getPushNotificationExpirydate() {
		return pushNotificationExpirydate;
	}

	/**
	 * @param pushNotificationExpirydate the pushNotificationExpirydate to set
	 */
	public void setPushNotificationExpirydate(Date pushNotificationExpirydate) {
		this.pushNotificationExpirydate = pushNotificationExpirydate;
	}

	/**
	 * @return the pushNotificationViewedDate
	 */
	public Date getPushNotificationViewedDate() {
		return pushNotificationViewedDate;
	}

	/**
	 * @param pushNotificationViewedDate the pushNotificationViewedDate to set
	 */
	public void setPushNotificationViewedDate(Date pushNotificationViewedDate) {
		this.pushNotificationViewedDate = pushNotificationViewedDate;
	}

	/**
	 * @return the pushNotificationBounceDate
	 */
	public Date getPushNotificationBounceDate() {
		return pushNotificationBounceDate;
	}

	/**
	 * @param pushNotificationBounceDate the pushNotificationBounceDate to set
	 */
	public void setPushNotificationBounceDate(Date pushNotificationBounceDate) {
		this.pushNotificationBounceDate = pushNotificationBounceDate;
	}

	/**
	 * @return the pushNotificationHeld
	 */
	public boolean isPushNotificationHeld() {
		return pushNotificationHeld;
	}

	/**
	 * @param pushNotificationHeld the pushNotificationHeld to set
	 */
	public void setPushNotificationHeld(boolean pushNotificationHeld) {
		this.pushNotificationHeld = pushNotificationHeld;
	}

	/**
	 * @return the groupPushNotificationAccount
	 */
	public GroupPushNotificationAccount getGroupPushNotificationAccount() {
		return groupPushNotificationAccount;
	}

	/**
	 * @param groupPushNotificationAccount the groupPushNotificationAccount to set
	 */
	public void setGroupPushNotificationAccount(
			GroupPushNotificationAccount groupPushNotificationAccount) {
		this.groupPushNotificationAccount = groupPushNotificationAccount;
	}

	/**
	 * @return the groupEventInviteId
	 */
	public String getGroupEventInviteId() {
		return groupEventInviteId;
	}

	/**
	 * @param groupEventInviteId the groupEventInviteId to set
	 */
	public void setGroupEventInviteId(String groupEventInviteId) {
		this.groupEventInviteId = groupEventInviteId;
	}

	/**
	 * @return the pushNotificationError
	 */
	public String getPushNotificationError() {
		return pushNotificationError;
	}

	/**
	 * @param pushNotificationError the pushNotificationError to set
	 */
	public void setPushNotificationError(String pushNotificationError) {
		this.pushNotificationError = pushNotificationError;
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
	 * @return the groupMember
	 */
	public GroupMember getGroupMember() {
		return groupMember;
	}

	/**
	 * @param groupMember the groupMember to set
	 */
	public void setGroupMember(GroupMember groupMember) {
		this.groupMember = groupMember;
	}

	/**
	 * @return the group
	 */
	public Groups getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Groups group) {
		this.group = group;
	}
	
	
}
