/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_sms")
public class GroupSMS extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6115591459760034361L;

	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "groupSmsId", unique = true, updatable = false)
	private String groupSmsId;
	
	@Column
	private String providerMessageId;
	
	@Column @NotNull @NotEmpty
	private String mobileNumber;
	
	
	@Column @NotNull @NotEmpty
	private String body;
	
	
	/* Date when the email is to be sent */
	@Column
	private Date smsingDate;
	
	/* Actual Date when email is sent */
	@Column
	private Date smsedDate;
	
	/*Date after which email should not be sent*/
	@Column
	private Date smsExpirydate; 
	
	@Column
	private Date smsDeliveredDate;
	
	@Column @NotNull @NotEmpty
	private String groupCode;
	
	@Column
	private boolean smsHeld;
	
	@Column
	private String smsAccountCode;
	
	@Column
	private String groupEventInviteId;
	
	@Column
	private String smsError;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "serialNumber")
	private GroupMember groupMember;

	/**
	 * @return the groupSmsId
	 */
	public String getGroupSmsId() {
		return groupSmsId;
	}

	/**
	 * @param groupSmsId the groupSmsId to set
	 */
	public void setGroupSmsId(String groupSmsId) {
		this.groupSmsId = groupSmsId;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the smsingDate
	 */
	public Date getSmsingDate() {
		return smsingDate;
	}

	/**
	 * @param smsingDate the smsingDate to set
	 */
	public void setSmsingDate(Date smsingDate) {
		this.smsingDate = smsingDate;
	}

	/**
	 * @return the smsedDate
	 */
	public Date getSmsedDate() {
		return smsedDate;
	}

	/**
	 * @param smsedDate the smsedDate to set
	 */
	public void setSmsedDate(Date smsedDate) {
		this.smsedDate = smsedDate;
	}

	/**
	 * @return the smsExpirydate
	 */
	public Date getSmsExpirydate() {
		return smsExpirydate;
	}

	/**
	 * @param smsExpirydate the smsExpirydate to set
	 */
	public void setSmsExpirydate(Date smsExpirydate) {
		this.smsExpirydate = smsExpirydate;
	}

	/**
	 * @return the smsDeliveredDate
	 */
	public Date getSmsDeliveredDate() {
		return smsDeliveredDate;
	}

	/**
	 * @param smsDeliveredDate the smsDeliveredDate to set
	 */
	public void setSmsDeliveredDate(Date smsDeliveredDate) {
		this.smsDeliveredDate = smsDeliveredDate;
	}

	/**
	 * @return the smsHeld
	 */
	public boolean isSmsHeld() {
		return smsHeld;
	}

	/**
	 * @param smsHeld the smsHeld to set
	 */
	public void setSmsHeld(boolean smsHeld) {
		this.smsHeld = smsHeld;
	}

	/**
	 * @return the smsAccountCode
	 */
	public String getSmsAccountCode() {
		return smsAccountCode;
	}

	/**
	 * @param smsAccountCode the smsAccountCode to set
	 */
	public void setSmsAccountCode(String smsAccountCode) {
		this.smsAccountCode = smsAccountCode;
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
	 * @return the smsError
	 */
	public String getSmsError() {
		return smsError;
	}

	/**
	 * @param smsError the smsError to set
	 */
	public void setSmsError(String smsError) {
		this.smsError = smsError;
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
	 * @return the providerMessageId
	 */
	public String getProviderMessageId() {
		return providerMessageId;
	}

	/**
	 * @param providerMessageId the providerMessageId to set
	 */
	public void setProviderMessageId(String providerMessageId) {
		this.providerMessageId = providerMessageId;
	}

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


/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = GroupEmailActivity.class)
	@JoinColumn(name = "groupEmailId", referencedColumnName = "groupEmailId")
	private List<GroupEmailActivity> groupEmailActivity;*/
	
/*	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "groupEventInviteId")
	private GroupEventInvite groupEventInvite;*/


}
