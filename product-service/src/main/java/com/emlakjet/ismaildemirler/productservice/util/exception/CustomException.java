package com.emlakjet.ismaildemirler.productservice.util.exception;

public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7855988983071470578L;

	public CustomException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
