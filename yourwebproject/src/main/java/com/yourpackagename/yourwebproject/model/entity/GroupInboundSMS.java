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
@Table(name = "group_inbound_sms")
public class GroupInboundSMS extends NoIDEntity implements Serializable {

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
	@Column(name = "groupInboundSmsId", unique = true, updatable = false)
	private String groupInboundSmsId;
	
	@Column @NotNull @NotEmpty
	private String smsContent;
	
	@Column
	private String providerMessageId;

	@Column
	private Date smsedDate;
	
	@Column @NotNull @NotEmpty
	private String groupCode;
	
	@Column
	private String smsAccountCode;
	
	@Column
	private String groupEventInviteId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "serialNumber")
	private GroupMember groupMember;

	/**
	 * @return the groupInboundSmsId
	 */
	public String getGroupInboundSmsId() {
		return groupInboundSmsId;
	}

	/**
	 * @param groupInboundSmsId the groupInboundSmsId to set
	 */
	public void setGroupInboundSmsId(String groupInboundSmsId) {
		this.groupInboundSmsId = groupInboundSmsId;
	}

	/**
	 * @return the smsContent
	 */
	public String getSmsContent() {
		return smsContent;
	}

	/**
	 * @param smsContent the smsContent to set
	 */
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
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


}
