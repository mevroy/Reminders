/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_content")
public class GroupContent extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1595764109716974714L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "contentId", unique = true, updatable = false)
	private String contentId;

	@Column
	private String contentURL;

	@Column
	private String contentName;

	@Column
	private String contentTitle;

	@Column(columnDefinition = "TEXT")
	private String pageContent;

	@Column
	private Date startDate;

	@Column
	private Date expiryDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupId")
	private Groups group;

	@Transient
	private boolean active;

	/**
	 * @return the contentId
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * @param contentId
	 *            the contentId to set
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return the contentURL
	 */
	public String getContentURL() {
		return contentURL;
	}

	/**
	 * @param contentURL
	 *            the contentURL to set
	 */
	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}

	/**
	 * @return the pageContent
	 */
	public String getPageContent() {
		return pageContent;
	}

	/**
	 * @param pageContent
	 *            the pageContent to set
	 */
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
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
	 * @param group
	 *            the group to set
	 */
	public void setGroup(Groups group) {
		this.group = group;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return CommonUtils.isValidDates(this.startDate, this.expiryDate);
	}

	/**
	 * @return the contentName
	 */
	public String getContentName() {
		return contentName;
	}

	/**
	 * @param contentName
	 *            the contentName to set
	 */
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	/**
	 * @return the contentTitle
	 */
	public String getContentTitle() {
		return contentTitle;
	}

	/**
	 * @param contentTitle
	 *            the contentTitle to set
	 */
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

}
