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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.NoIDEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_event_passes")
public class GroupEventPass extends NoIDEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2610501038571466293L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, updatable = false)
	private String id;
	
	@Column(unique=true)
	@NotNull
	@NotEmpty
	private String passIdentifier;
	
	@Column
	private String passBarcode;
	
	@Column
	@NotNull
	@NotEmpty
	private String groupCode;
	
	@Column
	@NotNull
	@NotEmpty
	private String groupEventCode;
	
	@Column
	private Date trackingDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "serialNumber")
	private GroupMember groupMember;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupEventInviteId")
	private GroupEventInvite groupEventInvite;
	
	@Column
	private Date passStartDate;
	
	@Column
	private Date passExpiryDate;
	
	@Column
	private int noOfPeopleTagged;
	
	@Column
	private boolean passInvalidated;

	@Column
	private double passPrice;
	
	@Column
	private boolean sold;
	
	@Column
	private String soldBy;
	
	@Column
	private String tableNumber;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the passIdentifier
	 */
	public String getPassIdentifier() {
		return passIdentifier;
	}

	/**
	 * @param passIdentifier the passIdentifier to set
	 */
	public void setPassIdentifier(String passIdentifier) {
		this.passIdentifier = passIdentifier;
	}

	/**
	 * @return the passBarcode
	 */
	public String getPassBarcode() {
		return passBarcode;
	}

	/**
	 * @param passBarcode the passBarcode to set
	 */
	public void setPassBarcode(String passBarcode) {
		this.passBarcode = passBarcode;
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
	 * @return the trackingDate
	 */
	public Date getTrackingDate() {
		return trackingDate;
	}

	/**
	 * @param trackingDate the trackingDate to set
	 */
	public void setTrackingDate(Date trackingDate) {
		this.trackingDate = trackingDate;
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
	 * @return the noOfPeopleTagged
	 */
	public int getNoOfPeopleTagged() {
		return noOfPeopleTagged;
	}

	/**
	 * @param noOfPeopleTagged the noOfPeopleTagged to set
	 */
	public void setNoOfPeopleTagged(int noOfPeopleTagged) {
		this.noOfPeopleTagged = noOfPeopleTagged;
	}

	/**
	 * @return the passInvalidated
	 */
	public boolean isPassInvalidated() {
		return passInvalidated;
	}

	/**
	 * @param passInvalidated the passInvalidated to set
	 */
	public void setPassInvalidated(boolean passInvalidated) {
		this.passInvalidated = passInvalidated;
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
	 * @return the sold
	 */
	public boolean isSold() {
		return sold;
	}

	/**
	 * @param sold the sold to set
	 */
	public void setSold(boolean sold) {
		this.sold = sold;
	}

	/**
	 * @return the soldBy
	 */
	public String getSoldBy() {
		return soldBy;
	}

	/**
	 * @param soldBy the soldBy to set
	 */
	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}

	/**
	 * @return the tableNumber
	 */
	public String getTableNumber() {
		return tableNumber;
	}

	/**
	 * @param tableNumber the tableNumber to set
	 */
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	
	
}
