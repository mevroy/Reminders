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
@Table(name = "group_sms_template")
public class GroupSMSTemplate extends JpaEntity<Long> implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2564126613822043947L;


	@Column (unique=true, name="template_name") @NotNull @NotEmpty
	private String templateName;
	
	@Column(name="template_content", columnDefinition="TEXT") @NotNull
	private String templateContent;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="last_modified")
	private Date lastModified;
	
	@Column
	@NotNull
	private String smsAccountCode;
	
	@Column
	private String groupEventCode;
	
	@Column
	private String memberCategoryCode;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	private Groups group;

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
	 * @return the templateContent
	 */
	public String getTemplateContent() {
		return templateContent;
	}

	/**
	 * @param templateContent the templateContent to set
	 */
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
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
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
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


}
