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

import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_interests")
public class GroupInterests extends JpaEntity<Long> implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199814148282328984L;

	@Column
	@NotNull 
	@NotEmpty
	private String interestType;
	
	@Column
	private String interestDescription;
	
	@Column
	private Date startDate;
	
	@Column
	private Date expiryDate;
	
	@Column
	private boolean sendAutoResponse;
	
	@Column
	private String autoResponseTemplate;
	
	@Column
	private String defaultResponseTemplate;
	
	@Column
	private String url;
	
	@Column
	@NotNull 
	@NotEmpty
	private String groupCode;
	
	@Column
	private String memberCategoryCode;
	
	@Column
	private String eventCode;

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
	 * @return the interestDescription
	 */
	public String getInterestDescription() {
		return interestDescription;
	}

	/**
	 * @param interestDescription the interestDescription to set
	 */
	public void setInterestDescription(String interestDescription) {
		this.interestDescription = interestDescription;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
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
	 * @return the sendAutoResponse
	 */
	public boolean isSendAutoResponse() {
		return sendAutoResponse;
	}

	/**
	 * @param sendAutoResponse the sendAutoResponse to set
	 */
	public void setSendAutoResponse(boolean sendAutoResponse) {
		this.sendAutoResponse = sendAutoResponse;
	}


	/**
	 * @return the autoResponseTemplate
	 */
	public String getAutoResponseTemplate() {
		return autoResponseTemplate;
	}

	/**
	 * @param autoResponseTemplate the autoResponseTemplate to set
	 */
	public void setAutoResponseTemplate(String autoResponseTemplate) {
		this.autoResponseTemplate = autoResponseTemplate;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode the eventCode to set
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * @return the defaultResponseTemplate
	 */
	public String getDefaultResponseTemplate() {
		return defaultResponseTemplate;
	}

	/**
	 * @param defaultResponseTemplate the defaultResponseTemplate to set
	 */
	public void setDefaultResponseTemplate(String defaultResponseTemplate) {
		this.defaultResponseTemplate = defaultResponseTemplate;
	}
	
	
	
}
