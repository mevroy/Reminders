/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.framework.data.NoIDEntity;
import com.yourpackagename.yourwebproject.model.entity.enums.PaymentStatus;

/**
 * @author mdsouza
 *
 */
@Entity
@Table(name = "groupEventPaymentTransaction")
public class GroupEventPaymentTransaction extends NoIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8747470786696259299L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "transactionId", unique = true, updatable = false)
	private String transactionId;
	
	
	@Column(name="userReferenceNumber")
	private String userReferenceNumber;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupEventPaymentTypeId")
	private GroupEventPaymentType groupEventPaymentType;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	@Column(name="userCode")
	private String userCode;
	
	@Column(name="comments")
	private String comments;
	
	@Column
	private double transactionAmount;
	

	@Column
	private Date transactionDateTime;
	
	@Column
	private Date transactionExpiryDateTime;
	
	@Column
	private Date correspondenceDateTime;
	
	@Column
	private boolean transactionApproved;
	
	@Column
	private String groupEventCode;
	
	@Column
	private String groupCode;
	
	@Column(columnDefinition = "int default 0")
	private int totalNumberOfProducts;
	
	@Column
	private String errorMessage;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupEventInviteId")
	private GroupEventInvite groupEventInvite;

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the userReferenceNumber
	 */
	public String getUserReferenceNumber() {
		return userReferenceNumber;
	}

	/**
	 * @param userReferenceNumber the userReferenceNumber to set
	 */
	public void setUserReferenceNumber(String userReferenceNumber) {
		this.userReferenceNumber = userReferenceNumber;
	}


	/**
	 * @return the groupEventPaymentType
	 */
	public GroupEventPaymentType getGroupEventPaymentType() {
		return groupEventPaymentType;
	}

	/**
	 * @param groupEventPaymentType the groupEventPaymentType to set
	 */
	public void setGroupEventPaymentType(GroupEventPaymentType groupEventPaymentType) {
		this.groupEventPaymentType = groupEventPaymentType;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
	 * @return the transactionAmount
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
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
	 * @return the transactionExpiryDateTime
	 */
	public Date getTransactionExpiryDateTime() {
		return transactionExpiryDateTime;
	}

	/**
	 * @param transactionExpiryDateTime the transactionExpiryDateTime to set
	 */
	public void setTransactionExpiryDateTime(Date transactionExpiryDateTime) {
		this.transactionExpiryDateTime = transactionExpiryDateTime;
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
	 * @return the totalNumberOfProducts
	 */
	public int getTotalNumberOfProducts() {
		return totalNumberOfProducts;
	}

	/**
	 * @param totalNumberOfProducts the totalNumberOfProducts to set
	 */
	public void setTotalNumberOfProducts(int totalNumberOfProducts) {
		this.totalNumberOfProducts = totalNumberOfProducts;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	 * @return the correspondenceDateTime
	 */
	public Date getCorrespondenceDateTime() {
		return correspondenceDateTime;
	}

	/**
	 * @param correspondenceDateTime the correspondenceDateTime to set
	 */
	public void setCorrespondenceDateTime(Date correspondenceDateTime) {
		this.correspondenceDateTime = correspondenceDateTime;
	}

	/**
	 * @return the paymentStatus
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public static Comparator<GroupEventPaymentTransaction> transactionDateComparator = new Comparator<GroupEventPaymentTransaction>()
			{

				public int compare(GroupEventPaymentTransaction o1,
						GroupEventPaymentTransaction o2) {
					return o2.getTransactionDateTime().compareTo(o1.getTransactionDateTime());
				}
		
			};
	
}
