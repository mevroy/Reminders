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
import javax.persistence.Lob;
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
@Table(name = "group_email_template")
public class GroupEmailTemplate extends JpaEntity<Long> implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3766682642624482913L;

	@Column (unique=true, name="template_name") @NotNull @NotEmpty
	private String templateName;
	
	@Column(name="template_content", columnDefinition="TEXT") @NotNull
	private String templateContent;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="last_modified")
	private Date lastModified;

	@Column
	private String attachments;
	
	@Column
	private boolean prefillAttachments;
	
	@Column
	private boolean html;
	
	@Column
	@NotNull
	private String groupCode;
	
	@Column
	private String fromAlias;
	
	@Column
	private String fromAliasPersonalString;
	
	@Column
	private String replyToEmail;
	
	@Column
	@NotNull
	private String subject;
	
	@Column
	@NotNull
	private String emailAccountCode;
	
	@Column
	private String groupEventCode;
	
	@Column
	private String memberCategoryCode;
	
	@Column
	private boolean expressEmail;
	
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
	 * @return the prefillAttachments
	 */
	public boolean isPrefillAttachments() {
		return prefillAttachments;
	}

	/**
	 * @param prefillAttachments the prefillAttachments to set
	 */
	public void setPrefillAttachments(boolean prefillAttachments) {
		this.prefillAttachments = prefillAttachments;
	}


	
}
