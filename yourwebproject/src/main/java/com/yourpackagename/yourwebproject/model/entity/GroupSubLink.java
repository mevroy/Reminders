/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
// @FilterDef(name = "filterSubLinksOnGroupCode", parameters = { @ParamDef(name
// = "groupCode", type = "string") })
@Entity
@Table(name = "group_sub_links", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"linkName", "groupMainLinkId" }) })
public class GroupSubLink extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -661353833519798861L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, updatable = false)
	private String id;

	@Column
	@NotNull
	@NotEmpty
	private String linkName;

	@Column
	private String linkDescription;

	@Column
	private boolean disabled;

	@Column
	@NotEmpty
	@NotNull
	private String linkHref;

	@Column
	private String linkJavaScript;

	@Transient
	private boolean visibleToUser;

	@Transient
	private boolean divider;

	@Column
	private double linkOrder;

	@Transient
	private String url;

	@Transient
	private String javascript;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupMainLinkId")
	private GroupMainLink groupMainLink;

	// @Filter(name = "filterSubLinksOnGroupCode", condition =
	// "group_code = :groupCode")
	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(targetEntity = GroupLinkAccess.class)
	 * 
	 * @LazyCollection(LazyCollectionOption.TRUE)
	 * 
	 * @JoinColumn(name = "groupSubLinkId", referencedColumnName = "id") private
	 * List<GroupLinkAccess> groupLinkAccess;
	 */

	@Transient
	private List<GroupLinkAccess> groupLinkAccessForUI;

	/**
	 * @return the linkName
	 */
	public String getLinkName() {
		return linkName;
	}

	/**
	 * @param linkName
	 *            the linkName to set
	 */
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	/**
	 * @return the linkDescription
	 */
	public String getLinkDescription() {
		return linkDescription;
	}

	/**
	 * @param linkDescription
	 *            the linkDescription to set
	 */
	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}

	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * @param disabled
	 *            the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return the linkOrder
	 */
	public double getLinkOrder() {
		return linkOrder;
	}

	/**
	 * @param linkOrder
	 *            the linkOrder to set
	 */
	public void setLinkOrder(double linkOrder) {
		this.linkOrder = linkOrder;
	}

	/**
	 * @return the groupMainLink
	 */
	public GroupMainLink getGroupMainLink() {
		return groupMainLink;
	}

	/**
	 * @param groupMainLink
	 *            the groupMainLink to set
	 */
	public void setGroupMainLink(GroupMainLink groupMainLink) {
		this.groupMainLink = groupMainLink;
	}

	/*	*//**
	 * @return the groupLinkAccess
	 */
	/*
	 * public List<GroupLinkAccess> getGroupLinkAccess() { return
	 * groupLinkAccess; }
	 *//**
	 * @param groupLinkAccess
	 *            the groupLinkAccess to set
	 */
	/*
	 * public void setGroupLinkAccess(List<GroupLinkAccess> groupLinkAccess) {
	 * this.groupLinkAccess = groupLinkAccess; }
	 */

	/**
	 * @return the visibleToUser
	 */
	public boolean isVisibleToUser() {
		if ((CollectionUtils.isNotEmpty(this.groupLinkAccessForUI) && !this
				.isDisabled()) || this.isDivider()) {
			return true;
		}
		return false;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		if (CollectionUtils.isNotEmpty(this.groupLinkAccessForUI)) {
			String modifiedURL = this.getGroupLinkAccessForUI().get(0)
					.getLinkHref();
			if (StringUtils.isNotBlank(modifiedURL)) {
				Long id = this.getGroupLinkAccessForUI().get(0).getId();
				if (modifiedURL.indexOf("?") != -1)
					return modifiedURL + "&groupLinkAccessId=" + id;
				else {
					return modifiedURL + "?groupLinkAccessId=" + id;
				}
			}
		}
		return this.getLinkHref().indexOf("?") != -1 ? this.getLinkHref()
				+ "&groupSubLinkId=" + this.getId() : this.getLinkHref()
				+ "?groupSubLinkId=" + this.getId();
	}

	/**
	 * @return the javascript
	 */
	public String getJavascript() {
		if (CollectionUtils.isNotEmpty(this.groupLinkAccessForUI)) {
			if (StringUtils.isNotBlank(this.getGroupLinkAccessForUI().get(0)
					.getLinkJavaScript()))
				return this.getGroupLinkAccessForUI().get(0)
						.getLinkJavaScript();
		}
		return this.getLinkJavaScript();
	}

	/**
	 * @return the groupLinkAccessForUI
	 */
	public List<GroupLinkAccess> getGroupLinkAccessForUI() {
		return groupLinkAccessForUI;
	}

	/**
	 * @param groupLinkAccessForUI
	 *            the groupLinkAccessForUI to set
	 */
	public void setGroupLinkAccessForUI(
			List<GroupLinkAccess> groupLinkAccessForUI) {
		this.groupLinkAccessForUI = groupLinkAccessForUI;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the linkHref
	 */
	public String getLinkHref() {
		return linkHref;
	}

	/**
	 * @param linkHref
	 *            the linkHref to set
	 */
	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}

	/**
	 * @return the linkJavaScript
	 */
	public String getLinkJavaScript() {
		return linkJavaScript;
	}

	/**
	 * @param linkJavaScript
	 *            the linkJavaScript to set
	 */
	public void setLinkJavaScript(String linkJavaScript) {
		this.linkJavaScript = linkJavaScript;
	}

	/**
	 * @return the divider
	 */
	public boolean isDivider() {
		return this.linkName.indexOf("------") > -1;
	}

	public static Comparator<GroupSubLink> linkOrderComparatorAsc = new Comparator<GroupSubLink>() {

		public int compare(GroupSubLink g1, GroupSubLink g2) {

			double Lno1 = g1.getLinkOrder() * 100;
			double Lno2 = g2.getLinkOrder() * 100;

			/* For ascending order */
			return (int) (Lno1 - Lno2);

			/* For descending order */
			// Lno2-Lno1;
		}
	};

}
