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
@Table(name = "group_emails")
public class GroupEmail extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8747470786696259299L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "groupEmailId", unique = true, updatable = false)
	private String groupEmailId;
	
	@Column @Email @NotNull @NotEmpty
	private String emailAddress;
	
	@Column @NotNull @NotEmpty
	private String fromAlias;
	
	@Column
	private String fromAliasPersonalString;
	
	@Column 
	private String ccEmailAddress;
	
	@Column
	private String bccEmailAddress;
	
	@Column
	private String subject;
	
	@Column(columnDefinition="TEXT") @NotNull @NotEmpty
	@JsonIgnore
	private String body;
	
	@Column(columnDefinition="TEXT")
	private String prefillData;
	
	@Column
	private String footer;
	
	/* Date when the email is to be sent */
	@Column
	private Date emailingDate;
	
	/* Actual Date when email is sent */
	@Column
	private Date emailedDate;
	
	/*Date after which email should not be sent*/
	@Column
	private Date emailExpirydate; 
	
	@Column
	private Date emailViewedDate;
	
	@Column
	private Date emailBounceDate;
	
	@Column
	private String rsvpId;
	
	@Column
	private boolean html;
	
	@Column
	private boolean emailHeld;
	
	@Column
	private String replyToEmail;
	
	@Column @NotNull @NotEmpty
	private String emailAccountCode;
	
	@Column
	private String groupEventInviteId;
	
	@Column
	private String emailingError;
	
	@Column
	private String attachments;
	
	@Column
	private boolean expressEmail;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "serialNumber")
	private GroupMember groupMember;

/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = GroupEmailActivity.class)
	@JoinColumn(name = "groupEmailId", referencedColumnName = "groupEmailId")
	private List<GroupEmailActivity> groupEmailActivity;*/
	
/*	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "groupEventInviteId")
	private GroupEventInvite groupEventInvite;*/

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the fromAlias
	 */
	public String getFromAlias() {
		return fromAlias;
	}

	/**
	 * @param fromAlias the fromAlias to set
	 */
	public void setFromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}

	/**
	 * @return the ccEmailAddress
	 */
	public String getCcEmailAddress() {
		return ccEmailAddress;
	}

	/**
	 * @param ccEmailAddress the ccEmailAddress to set
	 */
	public void setCcEmailAddress(String ccEmailAddress) {
		this.ccEmailAddress = ccEmailAddress;
	}

	/**
	 * @return the bccEmailAddress
	 */
	public String getBccEmailAddress() {
		return bccEmailAddress;
	}

	/**
	 * @param bccEmailAddress the bccEmailAddress to set
	 */
	public void setBccEmailAddress(String bccEmailAddress) {
		this.bccEmailAddress = bccEmailAddress;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	 * @return the footer
	 */
	public String getFooter() {
		return footer;
	}

	/**
	 * @param footer the footer to set
	 */
	public void setFooter(String footer) {
		this.footer = footer;
	}

	/**
	 * @return the emailingDate
	 */
	public Date getEmailingDate() {
		return emailingDate;
	}

	/**
	 * @param emailingDate the emailingDate to set
	 */
	public void setEmailingDate(Date emailingDate) {
		this.emailingDate = emailingDate;
	}

	/**
	 * @return the emailedDate
	 */
	public Date getEmailedDate() {
		return emailedDate;
	}

	/**
	 * @param emailedDate the emailedDate to set
	 */
	public void setEmailedDate(Date emailedDate) {
		this.emailedDate = emailedDate;
	}

	/**
	 * @return the emailExpirydate
	 */
	public Date getEmailExpirydate() {
		return emailExpirydate;
	}

	/**
	 * @param emailExpirydate the emailExpirydate to set
	 */
	public void setEmailExpirydate(Date emailExpirydate) {
		this.emailExpirydate = emailExpirydate;
	}

	/**
	 * @return the emailViewedDate
	 */
	public Date getEmailViewedDate() {
		return emailViewedDate;
	}

	/**
	 * @param emailViewedDate the emailViewedDate to set
	 */
	public void setEmailViewedDate(Date emailViewedDate) {
		this.emailViewedDate = emailViewedDate;
	}

	/**
	 * @return the emailBounceDate
	 */
	public Date getEmailBounceDate() {
		return emailBounceDate;
	}

	/**
	 * @param emailBounceDate the emailBounceDate to set
	 */
	public void setEmailBounceDate(Date emailBounceDate) {
		this.emailBounceDate = emailBounceDate;
	}

	/**
	 * @return the rsvpId
	 */
	public String getRsvpId() {
		return rsvpId;
	}

	/**
	 * @param rsvpId the rsvpId to set
	 */
	public void setRsvpId(String rsvpId) {
		this.rsvpId = rsvpId;
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
	 * @return the html
	 */
	public boolean isHtml() {
		return html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(boolean html) {
		this.html = html;
	}

	/**
	 * @return the replyToEmail
	 */
	public String getReplyToEmail() {
		return replyToEmail;
	}

	/**
	 * @param replyToEmail the replyToEmail to set
	 */
	public void setReplyToEmail(String replyToEmail) {
		this.replyToEmail = replyToEmail;
	}

	/**
	 * @return the groupEmailId
	 */
	public String getGroupEmailId() {
		return groupEmailId;
	}

	/**
	 * @param groupEmailId the groupEmailId to set
	 */
	public void setGroupEmailId(String groupEmailId) {
		this.groupEmailId = groupEmailId;
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
	 * @return the emailHeld
	 */
	public boolean isEmailHeld() {
		return emailHeld;
	}

	/**
	 * @param emailHeld the emailHeld to set
	 */
	public void setEmailHeld(boolean emailHeld) {
		this.emailHeld = emailHeld;
	}

	/**
	 * @return the fromAliasPersonalString
	 */
	public String getFromAliasPersonalString() {
		return fromAliasPersonalString;
	}

	/**
	 * @param fromAliasPersonalString the fromAliasPersonalString to set
	 */
	public void setFromAliasPersonalString(String fromAliasPersonalString) {
		this.fromAliasPersonalString = fromAliasPersonalString;
	}

	/**
	 * @return the emailingError
	 */
	public String getEmailingError() {
		return emailingError;
	}

	/**
	 * @param emailingError the emailingError to set
	 */
	public void setEmailingError(String emailingError) {
		this.emailingError = emailingError;
	}

	/**
	 * @return the attachments
	 */
	public String getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the expressEmail
	 */
	public boolean isExpressEmail() {
		return expressEmail;
	}

	/**
	 * @param expressEmail the expressEmail to set
	 */
	public void setExpressEmail(boolean expressEmail) {
		this.expressEmail = expressEmail;
	}

	/**
	 * @return the prefillData
	 */
	public String getPrefillData() {
		return prefillData;
	}

	/**
	 * @param prefillData the prefillData to set
	 */
	public void setPrefillData(String prefillData) {
		this.prefillData = prefillData;
	}

/*	*//**
	 * @return the groupEmailActivity
	 *//*
	public List<GroupEmailActivity> getGroupEmailActivity() {
		return groupEmailActivity;
	}

	*//**
	 * @param groupEmailActivity the groupEmailActivity to set
	 *//*
	public void setGroupEmailActivity(List<GroupEmailActivity> groupEmailActivity) {
		this.groupEmailActivity = groupEmailActivity;
	}

*/
}
