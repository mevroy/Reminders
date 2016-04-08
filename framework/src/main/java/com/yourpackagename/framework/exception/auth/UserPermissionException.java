package com.yourpackagename.framework.exception.auth;

public class UserPermissionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public UserPermissionException(String message) {
		super(message);
	}
}
