package com.yourpackagename.yourwebproject.model.entity.enums;

/**
 * Types of payment  used for an event
 * Helps in controlling access to system elements.
 *
 * @author: Mevan D Souza
 * @created: 3/25/12 6:49 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public enum PaymentTypes {
	EFT(0, "EFT", "Payment by Electronic Fund transfer"),
	PAYPAL(1, "PAY","Payment via Paypal"), 
	VISAMASTERCARD(2, "VMC", "Payment via card");


	/** The state. */
	private final int state;
	private final String status;
	private final String describeStatus;
	/**
	 *
	 * @param state
	 *            the state
	 */
	PaymentTypes(int state, String status, String describeStatus) {
		this.state = state;
		this.status = status;
		this.describeStatus = describeStatus;
		
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the describeStatus
	 */
	public String getDescribeStatus() {
		return describeStatus;
	}



}
