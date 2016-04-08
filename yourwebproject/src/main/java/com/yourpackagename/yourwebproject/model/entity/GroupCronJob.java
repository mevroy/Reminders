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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mdsouza
 *
 */
@Entity
@Table(name = "group_cron_job")
public class GroupCronJob extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3766682642624482913L;

	@Column(unique = true)
	@NotNull
	@NotEmpty
	private String jobCode;

	@Column
	@NotNull
	private String jobName;

	@Column
	@NotNull
	private String jobDescription;

	@Column
	private String comments;

	@Column
	private String jobQueryString;

	@Column(unique = true)
	@NotNull
	private String jobCronName;

	@Column
	@NotNull
	private String jobCronExpression;

	@Column
	private String templateName;

	@Column
	private Date startDate;

	@Column
	private Date expiryDate;
	
	@Column
	private boolean disabled;

	@Column
	private Date lastExecutionDate;
	
	@Column
	private Date jobScheduleDate;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	private Groups group;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupMemberCategoryId")
	private GroupMemberCategory groupMemberCategory;

	@Column
	private String groupEventCode;
	
	@Column
	private String groupCode;
	
	@Column
	private String memberCategoryCode;
	
	@Column
	private String jobStatusContactEmail;
	
	
	/**
	 * @return the jobStatusContactEmail
	 */
	public String getJobStatusContactEmail() {
		return jobStatusContactEmail;
	}

	/**
	 * @param jobStatusContactEmail the jobStatusContactEmail to set
	 */
	public void setJobStatusContactEmail(String jobStatusContactEmail) {
		this.jobStatusContactEmail = jobStatusContactEmail;
	}

	/**
	 * @return the jobCode
	 */
	public String getJobCode() {
		return jobCode;
	}

	/**
	 * @param jobCode
	 *            the jobCode to set
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName
	 *            the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}

	/**
	 * @param jobDescription
	 *            the jobDescription to set
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the jobQueryString
	 */
	public String getJobQueryString() {
		return jobQueryString;
	}

	/**
	 * @param jobQueryString
	 *            the jobQueryString to set
	 */
	public void setJobQueryString(String jobQueryString) {
		this.jobQueryString = jobQueryString;
	}

	/**
	 * @return the jobCronName
	 */
	public String getJobCronName() {
		return jobCronName;
	}

	/**
	 * @param jobCronName
	 *            the jobCronName to set
	 */
	public void setJobCronName(String jobCronName) {
		this.jobCronName = jobCronName;
	}

	/**
	 * @return the jobCronExpression
	 */
	public String getJobCronExpression() {
		return jobCronExpression;
	}

	/**
	 * @param jobCronExpression
	 *            the jobCronExpression to set
	 */
	public void setJobCronExpression(String jobCronExpression) {
		this.jobCronExpression = jobCronExpression;
	}



	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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
	 * @return the groupMemberCategory
	 */
	public GroupMemberCategory getGroupMemberCategory() {
		return groupMemberCategory;
	}

	/**
	 * @param groupMemberCategory the groupMemberCategory to set
	 */
	public void setGroupMemberCategory(GroupMemberCategory groupMemberCategory) {
		this.groupMemberCategory = groupMemberCategory;
	}

	/**
	 * @return the memberCategoryCode
	 */
	public String getMemberCategoryCode() {
		return memberCategoryCode;
	}

	/**
	 * @param memberCategoryCode the memberCategoryCode to set
	 */
	public void setMemberCategoryCode(String memberCategoryCode) {
		this.memberCategoryCode = memberCategoryCode;
	}

	/**
	 * @return the groupEventCode
	 */
	public String getGroupEventCode() {
		return groupEventCode;
	}

	/**
	 * @param groupEventCode the groupEventCode to set
	 */
	public void setGroupEventCode(String groupEventCode) {
		this.groupEventCode = groupEventCode;
	}

	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return the lastExecutionDate
	 */
	public Date getLastExecutionDate() {
		return lastExecutionDate;
	}

	/**
	 * @param lastExecutionDate the lastExecutionDate to set
	 */
	public void setLastExecutionDate(Date lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}

	/**
	 * @return the jobScheduleDate
	 */
	public Date getJobScheduleDate() {
		return jobScheduleDate;
	}

	/**
	 * @param jobScheduleDate the jobScheduleDate to set
	 */
	public void setJobScheduleDate(Date jobScheduleDate) {
		this.jobScheduleDate = jobScheduleDate;
	}

	/*
	 * 
	 * 
	 * @ManyToOne(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "serialNumber" , referencedColumnName="serialNumber")
	 * private User user;
	 */
	/**
	 * @return the serialNumber
	 */
	/*
	 * public String getSerialNumber() { return serialNumber; }
	 *//**
	 * @param serialNumber
	 *            the serialNumber to set
	 */
	/*
	 * public void setSerialNumber(String serialNumber) { this.serialNumber =
	 * serialNumber; }
	 */

}
