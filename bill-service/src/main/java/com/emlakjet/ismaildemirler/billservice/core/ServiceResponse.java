package com.emlakjet.ismaildemirler.billservice.core;

import com.emlakjet.ismaildemirler.billservice.payload.PayloadModel;

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
	private static final long serialVersionUID = 4170551275019282312L;
	private boolean success;
	private String message;
	private Object error;
	private Object data;
}
