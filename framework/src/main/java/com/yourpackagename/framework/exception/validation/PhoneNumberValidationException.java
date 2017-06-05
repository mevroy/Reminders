package com.yourpackagename.framework.exception.validation;

public class PhoneNumberValidationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public PhoneNumberValidationException(String message) {
		super(message);
	}
}
