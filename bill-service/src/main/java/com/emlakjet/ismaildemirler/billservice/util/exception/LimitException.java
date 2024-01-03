package com.emlakjet.ismaildemirler.billservice.util.exception;

public class LimitException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2683559685498204472L;

	public LimitException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
