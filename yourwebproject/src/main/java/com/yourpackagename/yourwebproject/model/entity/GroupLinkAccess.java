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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@FilterDef(name="filterLinkAccessRoles", 
parameters=@ParamDef( name="roleType", type="string" ) )
@Table(name = "group_link_access",uniqueConstraints = {
	    @UniqueConstraint(columnNames={ "groupId","groupSubLinkId"})})
public class GroupLinkAccess extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1926681951483957351L;

	@Column
	private String linkHref;
	
	@Column
	private String linkJavaScript;
	
	@Column
	private Date startDate;
	
	@Column
	private Date expiryDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupId")
	private Groups group;
	
	@Column
	@NotNull
	@NotEmpty
	private String groupSubLinkId;
	
/*	@ManyToOne()
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupSubLinkId")
	private GroupSubLink groupSubLink ;*/
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity = GroupLinkAccessRole.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Filter(
			name = "filterLinkAccessRoles",
			condition="role = :roleType"
		)
	@JoinColumn(name = "groupLinkAccessId", referencedColumnName = "id")
	private List<GroupLinkAccessRole> groupLinkAccessRoles;

	/**
	 * @return the linkHref
	 */
	public String getLinkHref() {
		return linkHref;
	}

	/**
	 * @param linkHref the linkHref to set
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
	 * @param linkJavaScript the linkJavaScript to set
	 */
	public void setLinkJavaScript(String linkJavaScript) {
		this.linkJavaScript = linkJavaScript;
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

/*	*//**
	 * @return the groupSubLink
	 *//*
	public GroupSubLink getGroupSubLink() {
		return groupSubLink;
	}

	*//**
	 * @param groupSubLink the groupSubLink to set
	 *//*
	public void setGroupSubLink(GroupSubLink groupSubLink) {
		this.groupSubLink = groupSubLink;
	}
*/
	/**
	 * @return the groupLinkAccessRoles
	 */
	public List<GroupLinkAccessRole> getGroupLinkAccessRoles() {
		return groupLinkAccessRoles;
	}

	/**
	 * @param groupLinkAccessRoles the groupLinkAccessRoles to set
	 */
	public void setGroupLinkAccessRoles(
			List<GroupLinkAccessRole> groupLinkAccessRoles) {
		this.groupLinkAccessRoles = groupLinkAccessRoles;
	}

	/**
	 * @return the groupSubLinkId
	 */
	public String getGroupSubLinkId() {
		return groupSubLinkId;
	}

	/**
	 * @param groupSubLinkId the groupSubLinkId to set
	 */
	public void setGroupSubLinkId(String groupSubLinkId) {
		this.groupSubLinkId = groupSubLinkId;
	}


	
}
