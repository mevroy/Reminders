package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;
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

import org.hibernate.annotations.GenericGenerator;

import com.yourpackagename.framework.data.NoIDEntity;

@Entity
@Table(name = "group_event_invites")
public class GroupEventInvite extends NoIDEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2900028549296577243L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(unique = true, updatable = false)
	private String groupEventInviteId;

	@Column
	private String groupCode;
	
	@Column
	private String groupEventCode;
	
	@Column
	private String groupEventInviteCode;
	
	@Column
	private String memberCategoryCode;
	
	@Column
	private Date inviteStartDate;
	
	@Column
	private Date inviteExpiryDate;
	
	@Column
	private boolean inviteSent;
	
	@Column
	private boolean inviteDelivered;
	
	@Column
	private boolean inviteCancelled;
	
	@Column
	private boolean inviteHeld;
	
	@Column
	private boolean rsvpd;
	
	@Column
	private boolean markAttended;
	
	@Column
	private double paidAmount;
	
	@Column
	private String transactionReference;
	
	@Column
	private Date transactionDateTime;
	
	@Column
	private boolean transactionApproved;
	
	@Column
	private int inviteEmailCount;
	

/*	@Column
	private boolean resendInvite; */
	
	/* May be need  to establish connection betweengroupEmails Somehow*/
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "serialNumber")
	private GroupMember groupMember;
	
/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = GroupEmail.class)
	@JoinColumn(name = "groupEventInviteId", referencedColumnName = "groupEventInviteId")
	private List<GroupEmail> groupEmails;*/

/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = GroupEventInviteRSVP.class)
	@JoinColumn(name = "groupEventInviteId", referencedColumnName = "groupEventInviteId")
	private List<GroupEventInviteRSVP> groupEventInviteRSVPs;*/
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
	 * @return the groupEventInviteCode
	 */
	public String getGroupEventInviteCode() {
		return groupEventInviteCode;
	}

	/**
	 * @param groupEventInviteCode the groupEventInviteCode to set
	 */
	public void setGroupEventInviteCode(String groupEventInviteCode) {
		this.groupEventInviteCode = groupEventInviteCode;
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
	 * @return the inviteStartDate
	 */
	public Date getInviteStartDate() {
		return inviteStartDate;
	}

	/**
	 * @param inviteStartDate the inviteStartDate to set
	 */
	public void setInviteStartDate(Date inviteStartDate) {
		this.inviteStartDate = inviteStartDate;
	}

	/**
	 * @return the inviteExpiryDate
	 */
	public Date getInviteExpiryDate() {
		return inviteExpiryDate;
	}

	/**
	 * @param inviteExpiryDate the inviteExpiryDate to set
	 */
	public void setInviteExpiryDate(Date inviteExpiryDate) {
		this.inviteExpiryDate = inviteExpiryDate;
	}

	/**
	 * @return the inviteSend
	 */
	public boolean isInviteSent() {
		return inviteSent;
	}

	/**
	 * @param inviteSend the inviteSend to set
	 */
	public void setInviteSent(boolean inviteSent) {
		this.inviteSent = inviteSent;
	}

	/**
	 * @return the inviteDelivered
	 */
	public boolean isInviteDelivered() {
		return inviteDelivered;
	}

	/**
	 * @param inviteDelivered the inviteDelivered to set
	 */
	public void setInviteDelivered(boolean inviteDelivered) {
		this.inviteDelivered = inviteDelivered;
	}

	/**
	 * @return the inviteCancelled
	 */
	public boolean isInviteCancelled() {
		return inviteCancelled;
	}

	/**
	 * @param inviteCancelled the inviteCancelled to set
	 */
	public void setInviteCancelled(boolean inviteCancelled) {
		this.inviteCancelled = inviteCancelled;
	}

	/**
	 * @return the inviteHeld
	 */
	public boolean isInviteHeld() {
		return inviteHeld;
	}

	/**
	 * @param inviteHeld the inviteHeld to set
	 */
	public void setInviteHeld(boolean inviteHeld) {
		this.inviteHeld = inviteHeld;
	}

/*	*//**
	 * @return the resendInvite
	 *//*
	public boolean isResendInvite() {
		return resendInvite;
	}

	*//**
	 * @param resendInvite the resendInvite to set
	 *//*
	public void setResendInvite(boolean resendInvite) {
		this.resendInvite = resendInvite;
	}*/

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
/*
	*//**
	 * @return the groupEmails
	 *//*
	public List<GroupEmail> getGroupEmails() {
		return groupEmails;
	}

	*//**
	 * @param groupEmails the groupEmails to set
	 *//*
	public void setGroupEmails(List<GroupEmail> groupEmails) {
		this.groupEmails = groupEmails;
	}
*/

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
	 * @return the markAttended
	 */
	public boolean isMarkAttended() {
		return markAttended;
	}

	/**
	 * @param markAttended the markAttended to set
	 */
	public void setMarkAttended(boolean markAttended) {
		this.markAttended = markAttended;
	}

	/**
	 * @return the paidAmount
	 */
	public double getPaidAmount() {
		return paidAmount;
	}

	/**
	 * @param paidAmount the paidAmount to set
	 */
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	/**
	 * @return the inviteEmailCount
	 */
	public int getInviteEmailCount() {
		return inviteEmailCount;
	}

	/**
	 * @param inviteEmailCount the inviteEmailCount to set
	 */
	public void setInviteEmailCount(int inviteEmailCount) {
		this.inviteEmailCount = inviteEmailCount;
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

	/**
	 * @return the transactionDateTime
	 */
	public Date getTransactionDateTime() {
		return transactionDateTime;
	}

	/**
	 * @param transactionDateTime the transactionDateTime to set
	 */
	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	/**
	 * @return the transactionApproved
	 */
	public boolean isTransactionApproved() {
		return transactionApproved;
	}

	/**
	 * @param transactionApproved the transactionApproved to set
	 */
	public void setTransactionApproved(boolean transactionApproved) {
		this.transactionApproved = transactionApproved;
	}


}
