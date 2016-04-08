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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_event_invite_rsvps")
public class GroupEventInviteRSVP extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8796248492154966473L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(unique = true, updatable = false)
	private String groupEventInviteRSVPId;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date rsvpDateTime;
	
	@Column
	private boolean rsvpd;
	
	@Column
	private String rsvpComments;
	
	@Column
	private int adultCount;
	
	@Column
	private int kidsCount;
	
	@Column
	private String rsvpOutcome;
	
	@Column
	private String groupCode;
	
	@Column
	private String memberCategoryCode;
	
	@Column
	private String groupEventCode;
	
	@Column
	private String transactionReference;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "serialNumber")
	private GroupMember groupMember;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupEventInviteId")
	private GroupEventInvite groupEventInvite;


	/**
	 * @return the groupEventInviteRSVPId
	 */
	public String getGroupEventInviteRSVPId() {
		return groupEventInviteRSVPId;
	}

	/**
	 * @param groupEventInviteRSVPId the groupEventInviteRSVPId to set
	 */
	public void setGroupEventInviteRSVPId(String groupEventInviteRSVPId) {
		this.groupEventInviteRSVPId = groupEventInviteRSVPId;
	}

	/**
	 * @return the rsvpDateTime
	 */
	public Date getRsvpDateTime() {
		return rsvpDateTime;
	}

	/**
	 * @param rsvpDateTime the rsvpDateTime to set
	 */
	public void setRsvpDateTime(Date rsvpDateTime) {
		this.rsvpDateTime = rsvpDateTime;
	}

	/**
	 * @return the rsvpd
	 */
	public boolean isRsvpd() {
		return rsvpd;
	}

	/**
	 * @param rsvpd the rsvpd to set
	 */
	public void setRsvpd(boolean rsvpd) {
		this.rsvpd = rsvpd;
	}

	/**
	 * @return the rsvpComments
	 */
	public String getRsvpComments() {
		return rsvpComments;
	}

	/**
	 * @param rsvpComments the rsvpComments to set
	 */
	public void setRsvpComments(String rsvpComments) {
		this.rsvpComments = rsvpComments;
	}

	/**
	 * @return the adultCount
	 */
	public int getAdultCount() {
		return adultCount;
	}

	/**
	 * @param adultCount the adultCount to set
	 */
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	/**
	 * @return the kidsCount
	 */
	public int getKidsCount() {
		return kidsCount;
	}

	/**
	 * @param kidsCount the kidsCount to set
	 */
	public void setKidsCount(int kidsCount) {
		this.kidsCount = kidsCount;
	}

	/**
	 * @return the rsvpOutcome
	 */
	public String getRsvpOutcome() {
		return rsvpOutcome;
	}

	/**
	 * @param rsvpOutcome the rsvpOutcome to set
	 */
	public void setRsvpOutcome(String rsvpOutcome) {
		this.rsvpOutcome = rsvpOutcome;
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
	 * @return the groupMember
	 */
	public GroupMember getGroupMember() {
		return groupMember;
	}

	/**
	 * @param groupMember the groupMember to set
	 */
	public void setGroupMember(GroupMember groupMember) {
		this.groupMember = groupMember;
	}

	/**
	 * @return the groupEventInvite
	 */
	public GroupEventInvite getGroupEventInvite() {
		return groupEventInvite;
	}

	/**
	 * @param groupEventInvite the groupEventInvite to set
	 */
	public void setGroupEventInvite(GroupEventInvite groupEventInvite) {
		this.groupEventInvite = groupEventInvite;
	}

	/**
	 * @return the transactionReference
	 */
	public String getTransactionReference() {
		return transactionReference;
	}

	/**
	 * @param transactionReference the transactionReference to set
	 */
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	
	
	
}
