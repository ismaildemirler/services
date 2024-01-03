package com.emlakjet.ismaildemirler.productservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse implements PayloadModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8098413730352835567L;
	private boolean success;
	private String message;
	private Object error;
	private Object data;
}