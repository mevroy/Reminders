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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.yourpackagename.framework.data.JpaEntity;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_events")
public class GroupEvents extends JpaEntity<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6604779498320729417L;

	@Column(unique = true)
	@NotNull
	@NotEmpty
	private String eventCode;

	@Column
	@DateTimeFormat(pattern = "dd-MM-yyyy'T'hh:mm")
	private Date eventDate;

	@Column
	private String description;

	@Column
	private String eventName;

	
	@Column
	private boolean emailRsvpAllowed;

	@Column
	@DateTimeFormat(pattern = "dd-MM-yyyy'T'hh:mm")
	private Date rsvpDeadlineDate;

	@Column
	private String reminderFrequency;

	@Column
	@NotNull
	@NotBlank
	private String groupCode;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	private Groups group;

	@Column
	private String memberCategoryCode;

	/*
	 * @ManyToOne(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "memberCategoryCode" ,
	 * referencedColumnName="memberCategoryCode") private GroupMemberCategory
	 * groupMemberCategory;
	 */

	@Column
	private String eventOrganiser;

	@Column
	private boolean outsideParticipationAllowed;

	@Column
	private boolean paidEvent;

	@Column
	private int transactionExpiryInMinutes;
	
	@Column
	private double amountPerAdulthead;

	@Column
	private double amountPerKidHead;
	
	@Column
	private double amountPerNMAdulthead;

	@Column
	private double amountPerNMKidHead;

	@Column
	private double amountPerFamily;

	@Column
	private boolean eventInviteSentImmediately;

	@Column
	private boolean autoResponseForRSVPAllowed;

	@Column 
	private String autoResponseRSVPTemplate;
	
	@Column
	private String processCompletionTemplate;
	
	@Column(columnDefinition = "int default 0")
	private int groupEventInviteCodeLength;
	
	@Column(columnDefinition = "int default 0")
	private int maxNumberOfPasses;
	
	@Column
	private Date expiryDate;
	
	@Column
	private String externalSurveyRedirectUrl;
	
	@Transient
	@JsonIgnore
	private List<GroupEventPassCategory> groupEventPassCategories;
	
	@OneToMany(targetEntity = GroupEventPaymentType.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupEventId", referencedColumnName = "id")
	private List<GroupEventPaymentType> groupEventPaymentTypes;
	
	/**
	 * @return the eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode
	 *            the eventCode to set
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate
	 *            the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the emailRsvpAllowed
	 */
	public boolean isEmailRsvpAllowed() {
		return emailRsvpAllowed;
	}

	/**
	 * @param emailRsvpAllowed
	 *            the emailRsvpAllowed to set
	 */
	public void setEmailRsvpAllowed(boolean emailRsvpAllowed) {
		this.emailRsvpAllowed = emailRsvpAllowed;
	}

	/**
	 * @return the rsvpDeadlineDate
	 */
	public Date getRsvpDeadlineDate() {
		return rsvpDeadlineDate;
	}

	/**
	 * @param rsvpDeadlineDate
	 *            the rsvpDeadlineDate to set
	 */
	public void setRsvpDeadlineDate(Date rsvpDeadlineDate) {
		this.rsvpDeadlineDate = rsvpDeadlineDate;
	}

	/**
	 * @return the reminderFrequency
	 */
	public String getReminderFrequency() {
		return reminderFrequency;
	}

	/**
	 * @param reminderFrequency
	 *            the reminderFrequency to set
	 */
	public void setReminderFrequency(String reminderFrequency) {
		this.reminderFrequency = reminderFrequency;
	}

	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the group
	 */
	public Groups getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(Groups group) {
		this.group = group;
	}

	/**
	 * @return the memberCategoryCode
	 */
	public String getMemberCategoryCode() {
		return memberCategoryCode;
	}

	/**
	 * @param memberCategoryCode
	 *            the memberCategoryCode to set
	 */
	public void setMemberCategoryCode(String memberCategoryCode) {
		this.memberCategoryCode = memberCategoryCode;
	}

	/*	*//**
	 * @return the groupMemberCategory
	 */
	/*
	 * public GroupMemberCategory getGroupMemberCategory() { return
	 * groupMemberCategory; }
	 *//**
	 * @param groupMemberCategory
	 *            the groupMemberCategory to set
	 */
	/*
	 * public void setGroupMemberCategory(GroupMemberCategory
	 * groupMemberCategory) { this.groupMemberCategory = groupMemberCategory; }
	 */
	/**
	 * @return the eventOrganiser
	 */
	public String getEventOrganiser() {
		return eventOrganiser;
	}

	/**
	 * @param eventOrganiser
	 *            the eventOrganiser to set
	 */
	public void setEventOrganiser(String eventOrganiser) {
		this.eventOrganiser = eventOrganiser;
	}

	/**
	 * @return the outsideParticipationAllowed
	 */
	public boolean isOutsideParticipationAllowed() {
		return outsideParticipationAllowed;
	}

	/**
	 * @param outsideParticipationAllowed
	 *            the outsideParticipationAllowed to set
	 */
	public void setOutsideParticipationAllowed(
			boolean outsideParticipationAllowed) {
		this.outsideParticipationAllowed = outsideParticipationAllowed;
	}

	/**
	 * @return the paidEvent
	 */
	public boolean isPaidEvent() {
		return paidEvent;
	}

	/**
	 * @param paidEvent
	 *            the paidEvent to set
	 */
	public void setPaidEvent(boolean paidEvent) {
		this.paidEvent = paidEvent;
	}

	/**
	 * @return the amountPerAdulthead
	 */
	public double getAmountPerAdulthead() {
		return amountPerAdulthead;
	}

	/**
	 * @param amountPerAdulthead
	 *            the amountPerAdulthead to set
	 */
	public void setAmountPerAdulthead(double amountPerAdulthead) {
		this.amountPerAdulthead = amountPerAdulthead;
	}

	/**
	 * @return the amountPerKidHead
	 */
	public double getAmountPerKidHead() {
		return amountPerKidHead;
	}

	/**
	 * @param amountPerKidHead
	 *            the amountPerKidHead to set
	 */
	public void setAmountPerKidHead(double amountPerKidHead) {
		this.amountPerKidHead = amountPerKidHead;
	}

	/**
	 * @return the eventInviteSentImmediately
	 */
	public boolean isEventInviteSentImmediately() {
		return eventInviteSentImmediately;
	}

	/**
	 * @param eventInviteSentImmediately
	 *            the eventInviteSentImmediately to set
	 */
	public void setEventInviteSentImmediately(boolean eventInviteSentImmediately) {
		this.eventInviteSentImmediately = eventInviteSentImmediately;
	}

	/**
	 * @return the autoResponseForRSVPAllowed
	 */
	public boolean isAutoResponseForRSVPAllowed() {
		return autoResponseForRSVPAllowed;
	}

	/**
	 * @param autoResponseForRSVPAllowed
	 *            the autoResponseForRSVPAllowed to set
	 */
	public void setAutoResponseForRSVPAllowed(boolean autoResponseForRSVPAllowed) {
		this.autoResponseForRSVPAllowed = autoResponseForRSVPAllowed;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the amountPerFamily
	 */
	public double getAmountPerFamily() {
		return amountPerFamily;
	}

	/**
	 * @param amountPerFamily the amountPerFamily to set
	 */
	public void setAmountPerFamily(double amountPerFamily) {
		this.amountPerFamily = amountPerFamily;
	}

	/**
	 * @return the autoResponseRSVPTemplate
	 */
	public String getAutoResponseRSVPTemplate() {
		return autoResponseRSVPTemplate;
	}

	/**
	 * @param autoResponseRSVPTemplate the autoResponseRSVPTemplate to set
	 */
	public void setAutoResponseRSVPTemplate(String autoResponseRSVPTemplate) {
		this.autoResponseRSVPTemplate = autoResponseRSVPTemplate;
	}

	/**
	 * @return the groupEventInviteCodeLength
	 */
	public int getGroupEventInviteCodeLength() {
		return groupEventInviteCodeLength;
	}

	/**
	 * @param groupEventInviteCodeLength the groupEventInviteCodeLength to set
	 */
	public void setGroupEventInviteCodeLength(int groupEventInviteCodeLength) {
		this.groupEventInviteCodeLength = groupEventInviteCodeLength;
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
	 * @return the externalSurveyRedirectUrl
	 */
	public String getExternalSurveyRedirectUrl() {
		return externalSurveyRedirectUrl;
	}

	/**
	 * @param externalSurveyRedirectUrl the externalSurveyRedirectUrl to set
	 */
	public void setExternalSurveyRedirectUrl(String externalSurveyRedirectUrl) {
		this.externalSurveyRedirectUrl = externalSurveyRedirectUrl;
	}

	/**
	 * @return the maxNumberOfPasses
	 */
	public int getMaxNumberOfPasses() {
		return maxNumberOfPasses;
	}

	/**
	 * @param maxNumberOfPasses the maxNumberOfPasses to set
	 */
	public void setMaxNumberOfPasses(int maxNumberOfPasses) {
		this.maxNumberOfPasses = maxNumberOfPasses;
	}

	/**
	 * @return the amountPerNMAdulthead
	 */
	public double getAmountPerNMAdulthead() {
		return amountPerNMAdulthead;
	}

	/**
	 * @param amountPerNMAdulthead the amountPerNMAdulthead to set
	 */
	public void setAmountPerNMAdulthead(double amountPerNMAdulthead) {
		this.amountPerNMAdulthead = amountPerNMAdulthead;
	}

	/**
	 * @return the amountPerNMKidHead
	 */
	public double getAmountPerNMKidHead() {
		return amountPerNMKidHead;
	}

	/**
	 * @param amountPerNMKidHead the amountPerNMKidHead to set
	 */
	public void setAmountPerNMKidHead(double amountPerNMKidHead) {
		this.amountPerNMKidHead = amountPerNMKidHead;
	}

	/**
	 * @return the groupEventPassCategories
	 */
	public List<GroupEventPassCategory> getGroupEventPassCategories() {
		return groupEventPassCategories;
	}

	/**
	 * @param groupEventPassCategories the groupEventPassCategories to set
	 */
	public void setGroupEventPassCategories(
			List<GroupEventPassCategory> groupEventPassCategories) {
		this.groupEventPassCategories = groupEventPassCategories;
	}

	/**
	 * @return the groupEventPaymentTypes
	 */
	public List<GroupEventPaymentType> getGroupEventPaymentTypes() {
		return groupEventPaymentTypes;
	}

	/**
	 * @param groupEventPaymentTypes the groupEventPaymentTypes to set
	 */
	public void setGroupEventPaymentTypes(
			List<GroupEventPaymentType> groupEventPaymentTypes) {
		this.groupEventPaymentTypes = groupEventPaymentTypes;
	}

	/**
	 * @return the processCompletionTemplate
	 */
	public String getProcessCompletionTemplate() {
		return processCompletionTemplate;
	}

	/**
	 * @param processCompletionTemplate the processCompletionTemplate to set
	 */
	public void setProcessCompletionTemplate(String processCompletionTemplate) {
		this.processCompletionTemplate = processCompletionTemplate;
	}

	/**
	 * @return the transactionExpiryInMinutes
	 */
	public int getTransactionExpiryInMinutes() {
		return transactionExpiryInMinutes;
	}

	/**
	 * @param transactionExpiryInMinutes the transactionExpiryInMinutes to set
	 */
	public void setTransactionExpiryInMinutes(int transactionExpiryInMinutes) {
		this.transactionExpiryInMinutes = transactionExpiryInMinutes;
	}


}
