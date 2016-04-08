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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.SqlEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "groups")
public class Groups extends SqlEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5970299070342759513L;

	@Column(unique = true)
	@Length(min = 3, max = 3)
	@NotNull
	@NotEmpty
	private String groupCode;

	@Column
	private String description;

	@Column
	@Length(max = 200)
	private String groupLongName;

	@Column
	private Date startDate;

	@Column
	private Date expiryDate;

	@Column
	private int avgMembersCount;

	@Column
	private boolean registrationEmailSend;

	@Column
	private boolean groupEmailSend;

	@Column
	private String groupAdmin;

	@Column
	@Email
	private String contactEmail;

	@Column
	@Length(max = 15)
	private String contactNumber;

	@Column
	@Length(max = 30)
	private String contactName;
	
	@Column
	private String css;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupEvents.class)
	@JoinColumn(name = "groupId", referencedColumnName = "id")
	private List<GroupEvents> groupEvents;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupMemberCategory.class)
	@JoinColumn(name = "groupId", referencedColumnName = "id")
	private List<GroupMemberCategory> groupMemberCategory;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupMember.class)
	@JoinColumn(name = "groupId", referencedColumnName = "id")
	private List<GroupMember> groupMembers;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupEmailTemplate.class)
	@JoinColumn(name = "groupId", referencedColumnName = "id")
	private List<GroupEmailTemplate> groupEmailTemplates;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupCronJob.class)
	@JoinColumn(name = "groupId", referencedColumnName = "id")
	private List<GroupCronJob> groupCronJob;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the groupLongName
	 */
	public String getGroupLongName() {
		return groupLongName;
	}

	/**
	 * @param groupLongName
	 *            the groupLongName to set
	 */
	public void setGroupLongName(String groupLongName) {
		this.groupLongName = groupLongName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the avgMembersCount
	 */
	public int getAvgMembersCount() {
		return avgMembersCount;
	}

	/**
	 * @param avgMembersCount
	 *            the avgMembersCount to set
	 */
	public void setAvgMembersCount(int avgMembersCount) {
		this.avgMembersCount = avgMembersCount;
	}

	/**
	 * @return the registrationEmailSend
	 */
	public boolean isRegistrationEmailSend() {
		return registrationEmailSend;
	}

	/**
	 * @param registrationEmailSend
	 *            the registrationEmailSend to set
	 */
	public void setRegistrationEmailSend(boolean registrationEmailSend) {
		this.registrationEmailSend = registrationEmailSend;
	}

	/**
	 * @return the groupEmailSend
	 */
	public boolean isGroupEmailSend() {
		return groupEmailSend;
	}

	/**
	 * @param groupEmailSend
	 *            the groupEmailSend to set
	 */
	public void setGroupEmailSend(boolean groupEmailSend) {
		this.groupEmailSend = groupEmailSend;
	}

	/**
	 * @return the groupAdmin
	 */
	public String getGroupAdmin() {
		return groupAdmin;
	}

	/**
	 * @param groupAdmin
	 *            the groupAdmin to set
	 */
	public void setGroupAdmin(String groupAdmin) {
		this.groupAdmin = groupAdmin;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail
	 *            the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the groupMemberCategory
	 */
	public List<GroupMemberCategory> getGroupMemberCategory() {
		return groupMemberCategory;
	}

	/**
	 * @param groupMemberCategory
	 *            the groupMemberCategory to set
	 */
	public void setGroupMemberCategory(
			List<GroupMemberCategory> groupMemberCategory) {
		this.groupMemberCategory = groupMemberCategory;
	}

	/**
	 * @return the groupEvents
	 */
	public List<GroupEvents> getGroupEvents() {
		return groupEvents;
	}

	/**
	 * @param groupEvents
	 *            the groupEvents to set
	 */
	public void setGroupEvents(List<GroupEvents> groupEvents) {
		this.groupEvents = groupEvents;
	}

	/**
	 * @return the groupMembers
	 */
	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	/**
	 * @param groupMembers the groupMembers to set
	 */
	public void setGroupMembers(List<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}

	/**
	 * @return the groupEmailTemplates
	 */
	public List<GroupEmailTemplate> getGroupEmailTemplates() {
		return groupEmailTemplates;
	}

	/**
	 * @param groupEmailTemplates the groupEmailTemplates to set
	 */
	public void setGroupEmailTemplates(List<GroupEmailTemplate> groupEmailTemplates) {
		this.groupEmailTemplates = groupEmailTemplates;
	}

	/**
	 * @return the groupCronJob
	 */
	public List<GroupCronJob> getGroupCronJob() {
		return groupCronJob;
	}

	/**
	 * @param groupCronJob the groupCronJob to set
	 */
	public void setGroupCronJob(List<GroupCronJob> groupCronJob) {
		this.groupCronJob = groupCronJob;
	}

	/**
	 * @return the css
	 */
	public String getCss() {
		return css;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(String css) {
		this.css = css;
	}

}
