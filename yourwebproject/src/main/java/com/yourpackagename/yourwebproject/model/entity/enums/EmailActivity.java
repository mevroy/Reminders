package com.yourpackagename.yourwebproject.model.entity.enums;

/**
 * Types of users saved in this entity based on their role
 * Helps in controlling access to system elements.
 *
 * @author: Mevan D Souza
 * @created: 3/25/12 6:49 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public enum EmailActivity {
	CREATE(0, "Created", "Created By "),
	UPDATE(1, "Updated", "Updated By "),
	SENT(2, "Sent" , "Sent "),
	DELIVERED(3, "Delivered" , "Delivered "),
	DROPPED(4, "Dropped", "Dropped "),
	OPENED(5, "Opened", "Opened By "),
	BOUNCED(6, "Bounced", "Bounced "),
	CLICKED(7, "Clicked", "Clicked By "),
	DEFERRED(8, "Deferred", "Deferred "),
	PROCESSED(9, "Processed", "Processed ");
	/** The state. */
	private final int state;
	private final String activityType;
	private final String describeActivityType;
	/**
	 *
	 * @param state
	 *            the state
	 */
	EmailActivity(int state, String activityType, String describeActivityType) {
		this.state = state;
		this.activityType = activityType;
		this.describeActivityType = describeActivityType;
		
	}

	public int getState() {
		return state;
	}
	
	public String getActivityType()
	{
		return activityType;
	}
	
	public String getDescribeActivityType()
	{
		return describeActivityType;
	}

}
