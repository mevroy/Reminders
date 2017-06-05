/**
 * 
 */
package com.yourpackagename.yourwebproject.model.entity;

import java.io.Serializable;
import java.util.Date;





import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.yourwebproject.model.entity.enums.PaymentTypes;


/**
 * @author mdsouza
 *
 */
@Entity
@Table(name = "groupEventPaymentType")
public class GroupEventPaymentType extends JpaEntity<Long> implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3766682642624482913L;

	@Column
	private Date startDate;
	
	@Column
	private Date expiryDate;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentTypes paymentTypes;
	
	@Column(name="email")
	private String email;
	
	@Column(name="paymentAlias")
	private String paymentAlias;
	
	@Column(columnDefinition="TEXT")
	private String htmlContent;

	@ManyToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "groupId")
	private Groups group;
	
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
	 * @return the paymentTypes
	 */
	public PaymentTypes getPaymentTypes() {
		return paymentTypes;
	}

	/**
	 * @param paymentTypes the paymentTypes to set
	 */
	public void setPaymentTypes(PaymentTypes paymentTypes) {
		this.paymentTypes = paymentTypes;
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
	 * @return the paymentAlias
	 */
	public String getPaymentAlias() {
		return paymentAlias;
	}

	/**
	 * @param paymentAlias the paymentAlias to set
	 */
	public void setPaymentAlias(String paymentAlias) {
		this.paymentAlias = paymentAlias;
	}

	/**
	 * @return the htmlContent
	 */
	public String getHtmlContent() {
		return htmlContent;
	}

	/**
	 * @param htmlContent the htmlContent to set
	 */
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	
}
