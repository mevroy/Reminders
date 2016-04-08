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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_main_links")
public class GroupMainLink extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2277954623792501970L;

	@Column(unique = true)
	private String linkName;

	@Column
	private String linkDescription;

	@Column
	private boolean disabled;

	@Column
	private boolean overrideChildUrlorJs;

	@Transient
	private boolean visibleToUser;

	@Column
	private double linkOrder;

	@Transient
	private String url;

	@Transient
	private String javascript;

/*	@JsonIgnore
	@OneToMany(targetEntity = GroupSubLink.class)
	@LazyCollection(LazyCollectionOption.TRUE)
	@JoinColumn(name = "groupMainLinkId", referencedColumnName = "id")
	private List<GroupSubLink> groupSubLinks;*/

	@Transient
	private List<GroupSubLink> groupSubLinksForUI;

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

/*	*//**
	 * @return the groupSubLinks
	 *//*
	public List<GroupSubLink> getGroupSubLinks() {
		return groupSubLinks;
	}

	*//**
	 * @param groupSubLinks
	 *            the groupSubLinks to set
	 *//*
	public void setGroupSubLinks(List<GroupSubLink> groupSubLinks) {
		this.groupSubLinks = groupSubLinks;
	}*/

	/**
	 * @return the overrideChildUrlorJs
	 */
	public boolean isOverrideChildUrlorJs() {
		return overrideChildUrlorJs;
	}

	/**
	 * @param overrideChildUrlorJs
	 *            the overrideChildUrlorJs to set
	 */
	public void setOverrideChildUrlorJs(boolean overrideChildUrlorJs) {
		this.overrideChildUrlorJs = overrideChildUrlorJs;
	}

	/**
	 * @return the visibleToUser
	 */
	public boolean isVisibleToUser() {
		if (!this.isDisabled()) {
			if (CollectionUtils.isNotEmpty(this.groupSubLinksForUI)) {
				for (GroupSubLink groupSubLinkTemp : this.groupSubLinksForUI) {
					if (groupSubLinkTemp.isVisibleToUser() && !groupSubLinkTemp.isDivider())
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		if (this.overrideChildUrlorJs) {
			return "#";
		}
		return "";
	}

	/**
	 * @return the javascript
	 */
	public String getJavascript() {
		if (this.overrideChildUrlorJs) {
			return "#";
		}
		return null;
	}

	/**
	 * @return the groupSubLinksForUI
	 */
	public List<GroupSubLink> getGroupSubLinksForUI() {
		return groupSubLinksForUI;
	}

	/**
	 * @param groupSubLinksForUI
	 *            the groupSubLinksForUI to set
	 */
	public void setGroupSubLinksForUI(List<GroupSubLink> groupSubLinksForUI) {
		this.groupSubLinksForUI = groupSubLinksForUI;
	}

	public static Comparator<GroupMainLink> linkOrderComparatorAsc = new Comparator<GroupMainLink>() {

		public int compare(GroupMainLink g1, GroupMainLink g2) {

		   double Lno1 = g1.getLinkOrder()*100;
		   double Lno2 = g2.getLinkOrder()*100;

		   /*For ascending order*/
		   return (int)(Lno1-Lno2);

		   /*For descending order*/
		   //Lno2-Lno1;
	   }};
}
