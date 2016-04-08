package com.yourpackagename.yourwebproject.model.entity.enums;

/**
 * Types of users saved in this entity based on their role
 * Helps in controlling access to system elements.
 *
 * @author: Y Kamesh Rao
 * @created: 3/25/12 6:49 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public enum Role {
	SUPER_ADMIN(4),
	ADMIN(3),
	SUPER_USER(2),
	USER(1),
	ANONYMOUS(0);

	/** The state. */
	private final int state;

	/**
	 *
	 * @param state
	 *            the state
	 */
	Role(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

}
