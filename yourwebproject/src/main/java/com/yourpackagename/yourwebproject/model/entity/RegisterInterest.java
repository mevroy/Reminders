/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "register_interest")
public class RegisterInterest extends JpaEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8914970786088538999L;

	@Column
	@NotNull
	@Length(max = 25)
	private String firstName;

	@Column
	@Length(max = 15)
	private String middleName;

	@Column
	@Length(max = 25)
	private String lastName;

	@Column
	private String groupCode; // Need to define different groups Like MKC Board,
								// MKC Members, general interested members

	@Column
	private String interestType;
	
	@Column
	@NotNull
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
	private Date birthday;

	@Column
	private boolean completed;
	
	@Column
	private String campaign;
	
	@Column
	private String requestIp;
	
	@Column
	private boolean autoResponseEmailSent;
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
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
	 * @param middleName the middleName to set
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
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @return the interestType
	 */
	public String getInterestType() {
		return interestType;
	}

	/**
	 * @param interestType the interestType to set
	 */
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	/**
	 * @return the primaryEmail
	 */
	public String getPrimaryEmail() {
		return primaryEmail;
	}

	/**
	 * @param primaryEmail the primaryEmail to set
	 */
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	/**
	 * @return the primaryEmailVerified
	 */
	public boolean isPrimaryEmailVerified() {
		return primaryEmailVerified;
	}

	/**
	 * @param primaryEmailVerified the primaryEmailVerified to set
	 */
	public void setPrimaryEmailVerified(boolean primaryEmailVerified) {
		this.primaryEmailVerified = primaryEmailVerified;
	}

	/**
	 * @return the otherEmail
	 */
	public String getOtherEmail() {
		return otherEmail;
	}

	/**
	 * @param otherEmail the otherEmail to set
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
	 * @param mobilephone the mobilephone to set
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
	 * @param otherPhone the otherPhone to set
	 */
	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
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

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the campaign
	 */
	public String getCampaign() {
		return campaign;
	}

	/**
	 * @param campaign the campaign to set
	 */
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	/**
	 * @return the requestIp
	 */
	public String getRequestIp() {
		return requestIp;
	}

	/**
	 * @param requestIp the requestIp to set
	 */
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	/**
	 * @return the autoResponseEmailSent
	 */
	public boolean isAutoResponseEmailSent() {
		return autoResponseEmailSent;
	}

	/**
	 * @param autoResponseEmailSent the autoResponseEmailSent to set
	 */
	public void setAutoResponseEmailSent(boolean autoResponseEmailSent) {
		this.autoResponseEmailSent = autoResponseEmailSent;
	}
	
	
}
