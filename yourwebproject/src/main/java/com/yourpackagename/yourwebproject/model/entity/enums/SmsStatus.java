package com.yourpackagename.yourwebproject.model.entity.enums;

/**
 * Types of users saved in this entity based on their role
 * Helps in controlling access to system elements.
 *
 * @author: Mevan D Souza
 * @created: 3/25/12 6:49 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public enum SmsStatus {
	PEND(0, "PENDING", "The message is pending and has not yet been sent to the intended recipient"),
	SENT(1, "SENT","The message has been sent to the intended recipient, but has not been delivered yet"), 
	DELIVRD(2, "DELIVERED", "The message has been delivered to the intended recipient"),
	READ(3, "READ","The message has been received by the network and is being processed for delivery to the handset or the message has been received by the handset"),
	UNDVBL(4, "UNDELIVERABLE","SMS API was not able to deliver the message to the intended recipient on the specified channel"),
	NEW(5,"NEW","Sms is created and ready to be sent"),
	ERROR(6,"ERROR","Attempted delivery but errored");


	/** The state. */
	private final int state;
	private final String status;
	private final String describeStatus;
	/**
	 *
	 * @param state
	 *            the state
	 */
	SmsStatus(int state, String status, String describeStatus) {
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
