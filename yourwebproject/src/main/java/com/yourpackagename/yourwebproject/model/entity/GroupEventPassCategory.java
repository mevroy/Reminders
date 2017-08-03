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


import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;




import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_event_pass_category", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"passCategoryName", "groupEventId" }) })
public class GroupEventPassCategory extends NoIDEntity implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2097149909859270089L;

	

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "groupEventPassCategoryId", unique = true, updatable = false)
	private String groupEventPassCategoryId;
	

	
	@Column
	@Length(max = 50)
	private String passCategoryName;
	

	@Column
	@Length(max = 30)
	@NotNull
	@NotEmpty
	private String passCategoryNameShort;

	@Column
	private Date purchaseStartDateTime;
	
	@Column
	private Date purchaseExpiryDateTime;
	
	@Column
	private boolean disablePurchase;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupEventId")
	private GroupEvents groupEvent;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	private Groups group;
	
	@Column
	private Date passStartDate;
	
	@Column
	private Date passExpiryDate;
	
	@Column
	private boolean groupPass;

	@Column
	private boolean displayPrice;
	
	@Column
	private double passPrice;
	
	@Column
	@Length(max = 6)
	private String passPrefix;
	
	@Column
	@Length(max = 6)
	private String passSuffix;

	@Column
	private boolean randomPassNumbers;
	
	@Column
	private long numberOfPasses;
	
	@Column
	private String passImageURL;
	
	@Column
	private String passBarocodeURL;
	
	@Column
	private String passHeader;
	
	@Column
	private String passFooter;
	
	@Column
	private int maxPurchasePerInvite;
	
	@Column
	private String memberOnlyPurchase;

	@Column
	private double displayOrder;


	/**
	 * @return the groupEventPassCategoryId
	 */
	public String getGroupEventPassCategoryId() {
		return groupEventPassCategoryId;
	}

	/**
	 * @param groupEventPassCategoryId the groupEventPassCategoryId to set
	 */
	public void setGroupEventPassCategoryId(String groupEventPassCategoryId) {
		this.groupEventPassCategoryId = groupEventPassCategoryId;
	}

	/**
	 * @return the passCategoryName
	 */
	public String getPassCategoryName() {
		return passCategoryName;
	}

	/**
	 * @param passCategoryName the passCategoryName to set
	 */
	public void setPassCategoryName(String passCategoryName) {
		this.passCategoryName = passCategoryName;
	}

	/**
	 * @return the passCategoryNameShort
	 */
	public String getPassCategoryNameShort() {
		return passCategoryNameShort;
	}

	/**
	 * @param passCategoryNameShort the passCategoryNameShort to set
	 */
	public void setPassCategoryNameShort(String passCategoryNameShort) {
		this.passCategoryNameShort = passCategoryNameShort;
	}

	/**
	 * @return the purchaseStartDateTime
	 */
	public Date getPurchaseStartDateTime() {
		return purchaseStartDateTime;
	}

	/**
	 * @param purchaseStartDateTime the purchaseStartDateTime to set
	 */
	public void setPurchaseStartDateTime(Date purchaseStartDateTime) {
		this.purchaseStartDateTime = purchaseStartDateTime;
	}

	/**
	 * @return the purchaseExpiryDateTime
	 */
	public Date getPurchaseExpiryDateTime() {
		return purchaseExpiryDateTime;
	}

	/**
	 * @param purchaseExpiryDateTime the purchaseExpiryDateTime to set
	 */
	public void setPurchaseExpiryDateTime(Date purchaseExpiryDateTime) {
		this.purchaseExpiryDateTime = purchaseExpiryDateTime;
	}

	/**
	 * @return the disablePurchase
	 */
	public boolean isDisablePurchase() {
		return disablePurchase;
	}

	/**
	 * @param disablePurchase the disablePurchase to set
	 */
	public void setDisablePurchase(boolean disablePurchase) {
		this.disablePurchase = disablePurchase;
	}

	/**
	 * @return the groupEvent
	 */
	public GroupEvents getGroupEvent() {
		return groupEvent;
	}

	/**
	 * @param groupEvent the groupEvent to set
	 */
	public void setGroupEvent(GroupEvents groupEvent) {
		this.groupEvent = groupEvent;
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

	/**
	 * @return the passStartDate
	 */
	public Date getPassStartDate() {
		return passStartDate;
	}

	/**
	 * @param passStartDate the passStartDate to set
	 */
	public void setPassStartDate(Date passStartDate) {
		this.passStartDate = passStartDate;
	}

	/**
	 * @return the passExpiryDate
	 */
	public Date getPassExpiryDate() {
		return passExpiryDate;
	}

	/**
	 * @param passExpiryDate the passExpiryDate to set
	 */
	public void setPassExpiryDate(Date passExpiryDate) {
		this.passExpiryDate = passExpiryDate;
	}

	/**
	 * @return the groupPass
	 */
	public boolean isGroupPass() {
		return groupPass;
	}

	/**
	 * @param groupPass the groupPass to set
	 */
	public void setGroupPass(boolean groupPass) {
		this.groupPass = groupPass;
	}

	/**
	 * @return the passPrice
	 */
	public double getPassPrice() {
		return passPrice;
	}

	/**
	 * @param passPrice the passPrice to set
	 */
	public void setPassPrice(double passPrice) {
		this.passPrice = passPrice;
	}

	/**
	 * @return the passPrefix
	 */
	public String getPassPrefix() {
		return passPrefix;
	}

	/**
	 * @param passPrefix the passPrefix to set
	 */
	public void setPassPrefix(String passPrefix) {
		this.passPrefix = passPrefix;
	}

	/**
	 * @return the passSuffix
	 */
	public String getPassSuffix() {
		return passSuffix;
	}

	/**
	 * @param passSuffix the passSuffix to set
	 */
	public void setPassSuffix(String passSuffix) {
		this.passSuffix = passSuffix;
	}

	/**
	 * @return the randomPassNumbers
	 */
	public boolean isRandomPassNumbers() {
		return randomPassNumbers;
	}

	/**
	 * @param randomPassNumbers the randomPassNumbers to set
	 */
	public void setRandomPassNumbers(boolean randomPassNumbers) {
		this.randomPassNumbers = randomPassNumbers;
	}

	/**
	 * @return the numberOfPasses
	 */
	public long getNumberOfPasses() {
		return numberOfPasses;
	}

	/**
	 * @param numberOfPasses the numberOfPasses to set
	 */
	public void setNumberOfPasses(long numberOfPasses) {
		this.numberOfPasses = numberOfPasses;
	}

	/**
	 * @return the passImageURL
	 */
	public String getPassImageURL() {
		return passImageURL;
	}

	/**
	 * @param passImageURL the passImageURL to set
	 */
	public void setPassImageURL(String passImageURL) {
		this.passImageURL = passImageURL;
	}

	/**
	 * @return the passBarocodeURL
	 */
	public String getPassBarocodeURL() {
		return passBarocodeURL;
	}

	/**
	 * @param passBarocodeURL the passBarocodeURL to set
	 */
	public void setPassBarocodeURL(String passBarocodeURL) {
		this.passBarocodeURL = passBarocodeURL;
	}

	/**
	 * @return the passHeader
	 */
	public String getPassHeader() {
		return passHeader;
	}

	/**
	 * @param passHeader the passHeader to set
	 */
	public void setPassHeader(String passHeader) {
		this.passHeader = passHeader;
	}

	/**
	 * @return the passFooter
	 */
	public String getPassFooter() {
		return passFooter;
	}

	/**
	 * @param passFooter the passFooter to set
	 */
	public void setPassFooter(String passFooter) {
		this.passFooter = passFooter;
	}

	/**
	 * @return the displayPrice
	 */
	public boolean isDisplayPrice() {
		return displayPrice;
	}

	/**
	 * @param displayPrice the displayPrice to set
	 */
	public void setDisplayPrice(boolean displayPrice) {
		this.displayPrice = displayPrice;
	}

	/**
	 * @return the maxPurchasePerInvite
	 */
	public int getMaxPurchasePerInvite() {
		return maxPurchasePerInvite;
	}

	/**
	 * @param maxPurchasePerInvite the maxPurchasePerInvite to set
	 */
	public void setMaxPurchasePerInvite(int maxPurchasePerInvite) {
		this.maxPurchasePerInvite = maxPurchasePerInvite;
	}

	/**
	 * @return the memberOnlyPurchase
	 */
	public String getMemberOnlyPurchase() {
		return memberOnlyPurchase;
	}

	/**
	 * @param memberOnlyPurchase the memberOnlyPurchase to set
	 */
	public void setMemberOnlyPurchase(String memberOnlyPurchase) {
		this.memberOnlyPurchase = memberOnlyPurchase;
	}

	/**
	 * @return the displayOrder
	 */
	public double getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(double displayOrder) {
		this.displayOrder = displayOrder;
	}
	

}
