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
@Table(name = "group_push_notification_template")
public class GroupPushNotificationTemplate extends JpaEntity<Long> implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1341848912321357510L;

	/**
	 * 
	 */


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
	private String groupCode;
	
	@Column
	@NotNull
	private String pushNotificationsAccountCode;
	
	@Column
	private String groupEventCode;
	
	@Column
	private String memberCategoryCode;
	
	@Column
	private String pushNotificationsTargetType;

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
	 * @return the pushNotificationsAccountCode
	 */
	public String getPushNotificationsAccountCode() {
		return pushNotificationsAccountCode;
	}

	/**
	 * @param pushNotificationsAccountCode the pushNotificationsAccountCode to set
	 */
	public void setPushNotificationsAccountCode(String pushNotificationsAccountCode) {
		this.pushNotificationsAccountCode = pushNotificationsAccountCode;
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
	 * @return the pushNotificationsTargetType
	 */
	public String getPushNotificationsTargetType() {
		return pushNotificationsTargetType;
	}

	/**
	 * @param pushNotificationsTargetType the pushNotificationsTargetType to set
	 */
	public void setPushNotificationsTargetType(String pushNotificationsTargetType) {
		this.pushNotificationsTargetType = pushNotificationsTargetType;
	}
	


}
