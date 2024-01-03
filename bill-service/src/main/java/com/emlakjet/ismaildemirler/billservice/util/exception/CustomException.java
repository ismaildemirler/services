package com.emlakjet.ismaildemirler.billservice.util.exception;

public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2683559685498204472L;

	public CustomException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
