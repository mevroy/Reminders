package com.yourpackagename.yourwebproject.model.entity.enums;

/**
 * payment status  used for an event
 * Helps in controlling access to system elements.
 *
 * @author: Mevan D Souza
 * @created: 3/25/12 6:49 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public enum PaymentStatus {
	AWAITINGPMT(0, "Awaiting Payment", "Waiting for the payment to be received"),
	PROCESSINGPMT(1, "Processing Payment","Payment details received. Waiting for the transaction to come through"), 
	PMTRECEIVED(2, "Payment Received", "Payment received"),
	EXPIRED(3,"Transaction Expired","Transaction Expired"),
	PMTREJECTED(4,"Payment Rejected","Payment Rejected"),
	PROCESSED(5,"Processed","Correspondence has been sent to the member"),
	CANCELLED(6,"Cancelled","Cancelled"),
	APPROVED(7,"Approved","Approved");


	/** The state. */
	private final int state;
	private final String status;
	private final String describeStatus;
	/**
	 *
	 * @param state
	 *            the state
	 */
	PaymentStatus(int state, String status, String describeStatus) {
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
