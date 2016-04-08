/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_link_access_role",uniqueConstraints = {
	    @UniqueConstraint(columnNames={ "groupLinkAccessId","role"})})
public class GroupLinkAccessRole extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5512262951196083015L;

	@Column
	private Date startDate;
	
	@Column
	private Date expiryDate;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@JsonIgnore
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupLinkAccessId")
	private GroupLinkAccess groupLinkAccess ;

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
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the groupLinkAccess
	 */
	public GroupLinkAccess getGroupLinkAccess() {
		return groupLinkAccess;
	}

	/**
	 * @param groupLinkAccess the groupLinkAccess to set
	 */
	public void setGroupLinkAccess(GroupLinkAccess groupLinkAccess) {
		this.groupLinkAccess = groupLinkAccess;
	}
	
}
