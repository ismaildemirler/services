package com.emlakjet.ismaildemirler.productservice.util.exception;

public class DataNotFoundException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1025829936688534182L;

	public DataNotFoundException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
