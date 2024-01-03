package com.emlakjet.ismaildemirler.billservice.util.exception;

public class DataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2683559685498204472L;

	public DataNotFoundException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
