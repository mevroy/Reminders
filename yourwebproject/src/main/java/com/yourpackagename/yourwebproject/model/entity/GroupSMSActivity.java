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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;
import com.yourpackagename.yourwebproject.model.entity.enums.SmsStatus;

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_sms_activity")
public class GroupSMSActivity extends JpaEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2610204150586920918L;
	
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupSmsId")
	private GroupSMS groupSMS;
	
	@Column
	private String activityBy;
	
	@Column
	private Date activityTime;
	
	@Column
	@Enumerated(EnumType.STRING)
	private SmsStatus smsStatus;

	/**
	 * @return the groupEmail
	 */

	/**
	 * @return the activityBy
	 */
	public String getActivityBy() {
		return activityBy;
	}

	/**
	 * @param activityBy the activityBy to set
	 */
	public void setActivityBy(String activityBy) {
		this.activityBy = activityBy;
	}

	/**
	 * @return the activityTime
	 */
	public Date getActivityTime() {
		return activityTime;
	}

	/**
	 * @param activityTime the activityTime to set
	 */
	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	/**
	 * @return the groupSMS
	 */
	public GroupSMS getGroupSMS() {
		return groupSMS;
	}

	/**
	 * @param groupSMS the groupSMS to set
	 */
	public void setGroupSMS(GroupSMS groupSMS) {
		this.groupSMS = groupSMS;
	}

	/**
	 * @return the smsStatus
	 */
	public SmsStatus getSmsStatus() {
		return smsStatus;
	}

	/**
	 * @param smsStatus the smsStatus to set
	 */
	public void setSmsStatus(SmsStatus smsStatus) {
		this.smsStatus = smsStatus;
	}

	
	
}
