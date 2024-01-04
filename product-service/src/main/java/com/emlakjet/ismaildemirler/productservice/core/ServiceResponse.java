package com.emlakjet.ismaildemirler.productservice.core;

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
public class ServiceResponse {
	
	private boolean success;
	private String message;
	private Object error;
	private Object data;
}