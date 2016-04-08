/**
 * Group Member
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;







import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;







import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTimeUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.yourpackagename.framework.data.NoIDEntity;
import com.yourpackagename.yourwebproject.model.entity.embedded.Address;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_members")
public class GroupMember extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4914777399296610047L;

	/*
	 * @GeneratedValue(generator = "uuid")
	 * 
	 * @GenericGenerator(name = "uuid", strategy = "uuid2")
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "serialNumber", unique = true, updatable = false)
	private String serialNumber;


	
	@Column
	@NotNull
	@NotBlank
	@Length(max = 25)
	private String firstName;

	@Column
	@Length(max = 15)
	private String middleName;

	@Column
	@Length(max = 25)
	private String lastName;

	@Column
	@Length(max = 50)
	private String aliasName;

	@Column
	@NotNull
	@NotEmpty
	private String groupCode; // Need to define different groups Like MKC Board,
								// MKC Members, general interested members

	@Column
	@NotNull
	@NotEmpty
	@Email
	private String primaryEmail;

	@Column
	private boolean primaryEmailVerified;

	@Column
	private String otherEmail;

	@Column
	@Length(max = 13)
	private String mobilephone;

	@Column
	@Length(max = 13)
	private String otherPhone;

	@Column
	private int numberOfDependants;

	@Column
	private int adultCount;

	@Column
	private int kidCount;

	@Column 
	@DateTimeFormat(pattern = "dd-MM-yyyy'T'hh:mm")
	private Date membershipStartDate;

	@Column
	@DateTimeFormat(pattern = "dd-MM-yyyy'T'hh:mm")
	private Date membershipEndDate;

	@Column
	@NotNull
	private String memberCategoryCode; // Like board member, committee member ,
										// onboarding user etc

	@Embedded
	@Valid
	private Address address;

	@Column(insertable = true, updatable = false, unique = true )
	private String securityCode;

	@Column
	private boolean paidMember;

	@Column
	private double paidMembershipAmount;


	@Column
	private Date birthday;
	
	@Column(unique = true) 
	private String membershipIdentifier;
	
	@Transient
	private boolean activeMember;

	/*
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "serialNumber", referencedColumnName = "serialNumber")
	 * private List<User> users;
	 */

	@Column(nullable = true)
	private Date lastTransactionDate;
	
	@Column( nullable = true)
	private String comments;
	
	@Column (nullable = true)
	private String externalMembershipIdentifier;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	private Groups group;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupMemberCategoryId")
	private GroupMemberCategory groupMemberCategory;

/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = GroupEmail.class)
	@JoinColumn(name = "serialNumber", referencedColumnName = "serialNumber")
	private List<GroupEmail> groupEmails;*/

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = GroupDependents.class)
	@JoinColumn(name = "serialNumber", referencedColumnName = "serialNumber")
	private List<GroupDependents> groupDependents;
	
	
	/**
	 * @return the activeMember
	 */
	public boolean isActiveMember() {
		 return membershipEndDate!=null && Calendar.getInstance().getTime().before(membershipEndDate);
	}

	/**
	 * @param activeMember the activeMember to set
	 */
	public void setActiveMember(boolean activeMember) {
		this.activeMember = activeMember;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * @param aliasName
	 *            the aliasName to set
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
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
	 * @return the adultCount
	 */
	public int getAdultCount() {
		return adultCount;
	}

	/**
	 * @param adultCount
	 *            the adultCount to set
	 */
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	/**
	 * @return the kidCount
	 */
	public int getKidCount() {
		return kidCount;
	}

	/**
	 * @param kidCount
	 *            the kidCount to set
	 */
	public void setKidCount(int kidCount) {
		this.kidCount = kidCount;
	}

	/**
	 * @return the primaryEmail
	 */
	public String getPrimaryEmail() {
		return primaryEmail;
	}

	/**
	 * @param primaryEmail
	 *            the primaryEmail to set
	 */
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	/**
	 * @return the otherEmail
	 */
	public String getOtherEmail() {
		return otherEmail;
	}

	/**
	 * @param otherEmail
	 *            the otherEmail to set
	 */
	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}

	/**
	 * @return the mobilephone
	 */
	public String getMobilephone() {
		return mobilephone;
	}

	/**
	 * @param mobilephone
	 *            the mobilephone to set
	 */
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	/**
	 * @return the otherPhone
	 */
	public String getOtherPhone() {
		return otherPhone;
	}

	/**
	 * @param otherPhone
	 *            the otherPhone to set
	 */
	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	/**
	 * @return the numberOfDependants
	 */
	public int getNumberOfDependants() {
		return numberOfDependants;
	}

	/**
	 * @param numberOfDependants
	 *            the numberOfDependants to set
	 */
	public void setNumberOfDependants(int numberOfDependants) {
		this.numberOfDependants = numberOfDependants;
	}

	/**
	 * @return the membershipStartDate
	 */
	public Date getMembershipStartDate() {
		return membershipStartDate;
	}

	/**
	 * @param membershipStartDate
	 *            the membershipStartDate to set
	 */
	public void setMembershipStartDate(Date membershipStartDate) {
		this.membershipStartDate = membershipStartDate;
	}

	/**
	 * @return the membershipEndDate
	 */
	public Date getMembershipEndDate() {
		return membershipEndDate;
	}

	/**
	 * @param membershipEndDate
	 *            the membershipEndDate to set
	 */
	public void setMembershipEndDate(Date membershipEndDate) {
		this.membershipEndDate = membershipEndDate;
	}

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
	 * @return the securityCode
	 */
	public String getSecurityCode() {
		return securityCode;
	}

	/**
	 * @param securityCode
	 *            the securityCode to set
	 */

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	/*	*//**
	 * @return the users
	 */
	/*
	 * public List<User> getUsers() { return users; }
	 *//**
	 * @param users
	 *            the users to set
	 */
	/*
	 * public void setUsers(List<User> users) { this.users = users; }
	 */

	/**
	 * @return the paidMember
	 */
	public boolean isPaidMember() {
		return paidMember;
	}

	/**
	 * @param paidMember
	 *            the paidMember to set
	 */
	public void setPaidMember(boolean paidMember) {
		this.paidMember = paidMember;
	}

	/**
	 * @return the paidMembershipAmount
	 */
	public double getPaidMembershipAmount() {
		return paidMembershipAmount;
	}

	/**
	 * @param paidMembershipAmount
	 *            the paidMembershipAmount to set
	 */
	public void setPaidMembershipAmount(double paidMembershipAmount) {
		this.paidMembershipAmount = paidMembershipAmount;
	}

	/**
	 * @return the primaryEmailVerified
	 */
	public boolean isPrimaryEmailVerified() {
		return primaryEmailVerified;
	}

	/**
	 * @param primaryEmailVerified
	 *            the primaryEmailVerified to set
	 */
	public void setPrimaryEmailVerified(boolean primaryEmailVerified) {
		this.primaryEmailVerified = primaryEmailVerified;
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
	 * @return the groupMemberCategory
	 */
	public GroupMemberCategory getGroupMemberCategory() {
		return groupMemberCategory;
	}

	/**
	 * @param groupMemberCategory
	 *            the groupMemberCategory to set
	 */
	public void setGroupMemberCategory(GroupMemberCategory groupMemberCategory) {
		this.groupMemberCategory = groupMemberCategory;
	}

	/**
	 * @return the groupDependents
	 */
	public List<GroupDependents> getGroupDependents() {
		return groupDependents;
	}

	/**
	 * @param groupDependents the groupDependents to set
	 */
	public void setGroupDependents(List<GroupDependents> groupDependents) {
		this.groupDependents = groupDependents;
	}

	/**
	 * @return the membershipIdentifier
	 */
	public String getMembershipIdentifier() {
		return membershipIdentifier;
	}

	/**
	 * @param membershipIdentifier the membershipIdentifier to set
	 */
	public void setMembershipIdentifier(String membershipIdentifier) {
		this.membershipIdentifier = membershipIdentifier;
	}

	/**
	 * @return the lastTransactionDate
	 */
	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}

	/**
	 * @param lastTransactionDate the lastTransactionDate to set
	 */
	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the externalMembershipIdentifier
	 */
	public String getExternalMembershipIdentifier() {
		return externalMembershipIdentifier;
	}

	/**
	 * @param externalMembershipIdentifier the externalMembershipIdentifier to set
	 */
	public void setExternalMembershipIdentifier(String externalMembershipIdentifier) {
		this.externalMembershipIdentifier = externalMembershipIdentifier;
	}



}
