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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_member_category")
public class GroupMemberCategory extends JpaEntity<Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6048934997744568566L;

	@Column(unique = true)
	@NotNull
	@NotBlank
	private String memberCategoryCode;

	@Column
	private String memberCategoryName;
	
	@Column
	private String memberCategoryDescription;

	@Column
	private Date startDate;

	@Column
	private Date expiryDate;

	@Column
	@NotNull
	@NotBlank
	@Length(min = 3, max = 3)
	private String groupCode;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	private Groups group;

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupMember.class)
	@JoinColumn(name = "groupMemberCategoryId", referencedColumnName = "id")
	private List<GroupMember> groupMembers;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupCronJob.class)
	@JoinColumn(name = "groupMemberCategoryId", referencedColumnName = "id")
	private List<GroupCronJob> groupCronJobs;
	
	/**
	 * @return the memberCategoryCode
	 */
	public String getMemberCategoryCode() {
		return memberCategoryCode;
	}

	/**
	 * @param memberCategoryCode
	 *            the memberCategoryCode to set
	 */
	public void setMemberCategoryCode(String memberCategoryCode) {
		this.memberCategoryCode = memberCategoryCode;
	}

	/**
	 * @return the memberCategoryDescription
	 */
	public String getMemberCategoryDescription() {
		return memberCategoryDescription;
	}

	/**
	 * @param memberCategoryDescription
	 *            the memberCategoryDescription to set
	 */
	public void setMemberCategoryDescription(String memberCategoryDescription) {
		this.memberCategoryDescription = memberCategoryDescription;
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
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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
	 * @return the group
	 */
	public Groups getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(Groups group) {
		this.group = group;
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
	 * @return the groupCronJobs
	 */
	public List<GroupCronJob> getGroupCronJobs() {
		return groupCronJobs;
	}

	/**
	 * @param groupCronJobs the groupCronJobs to set
	 */
	public void setGroupCronJobs(List<GroupCronJob> groupCronJobs) {
		this.groupCronJobs = groupCronJobs;
	}

	/**
	 * @return the memberCategoryName
	 */
	public String getMemberCategoryName() {
		return memberCategoryName;
	}

	/**
	 * @param memberCategoryName the memberCategoryName to set
	 */
	public void setMemberCategoryName(String memberCategoryName) {
		this.memberCategoryName = memberCategoryName;
	}

}
