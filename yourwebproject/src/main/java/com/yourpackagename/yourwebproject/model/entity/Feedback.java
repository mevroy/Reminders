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

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mdsouza
 *
 */
@Entity
@Table(name = "feedback")
public class Feedback extends JpaEntity<Long> implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3766682642624482913L;

	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="comments")
	private String comments;
	
	@Column
	private String  improvementAreas;
	
	@Column
	private String compliments;
	
	@Column(name="serialNumber")
	private String serialNumber;
	
	@Column
	private String groupEventCode;
	
	@Column
	private String groupCode;
	
	@Column
	private String groupEventInviteId;
	
/*	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupEventInviteId")
	private GroupEventInvite groupEventInvite;*/

/*	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "serialNumber" , referencedColumnName="serialNumber")
	private User user;*/
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the name
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the improvementAreas
	 */
	public String getImprovementAreas() {
		return improvementAreas;
	}

	/**
	 * @param improvementAreas the improvementAreas to set
	 */
	public void setImprovementAreas(String improvementAreas) {
		this.improvementAreas = improvementAreas;
	}

	/**
	 * @return the compliments
	 */
	public String getCompliments() {
		return compliments;
	}

	/**
	 * @param compliments the compliments to set
	 */
	public void setCompliments(String compliments) {
		this.compliments = compliments;
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
	 * @return the groupEventInviteId
	 */
	public String getGroupEventInviteId() {
		return groupEventInviteId;
	}

	/**
	 * @param groupEventInviteId the groupEventInviteId to set
	 */
	public void setGroupEventInviteId(String groupEventInviteId) {
		this.groupEventInviteId = groupEventInviteId;
	}

/*	*//**
	 * @return the groupEventInvite
	 *//*
	public GroupEventInvite getGroupEventInvite() {
		return groupEventInvite;
	}

	*//**
	 * @param groupEventInvite the groupEventInvite to set
	 *//*
	public void setGroupEventInvite(GroupEventInvite groupEventInvite) {
		this.groupEventInvite = groupEventInvite;
	}
*/
/*	*//**
	 * @return the user
	 *//*
	public User getUser() {
		return user;
	}

	*//**
	 * @param user the user to set
	 *//*
	public void setUser(User user) {
		this.user = user;
	}

*/
	
	
}
