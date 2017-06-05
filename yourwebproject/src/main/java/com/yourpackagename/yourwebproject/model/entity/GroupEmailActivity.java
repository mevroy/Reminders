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

/**
 * @author mevan.d.souza
 *
 */
@Entity
@Table(name = "group_email_activity")
public class GroupEmailActivity extends JpaEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2610204150586920918L;
	
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupEmailId")
	private GroupEmail groupEmail;
	
	@Column
	private String activityBy;
	
	@Column
	private Date activityTime;
	
	@Column
	@Enumerated(EnumType.STRING)
	private EmailActivity emailActivity;

	/**
	 * @return the groupEmail
	 */
	public GroupEmail getGroupEmail() {
		return groupEmail;
	}

	/**
	 * @param groupEmail the groupEmail to set
	 */
	public void setGroupEmail(GroupEmail groupEmail) {
		this.groupEmail = groupEmail;
	}

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
	 * @return the emailActivity
	 */
	public EmailActivity getEmailActivity() {
		return emailActivity;
	}

	/**
	 * @param emailActivity the emailActivity to set
	 */
	public void setEmailActivity(EmailActivity emailActivity) {
		this.emailActivity = emailActivity;
	}
	
	
	
	
}
