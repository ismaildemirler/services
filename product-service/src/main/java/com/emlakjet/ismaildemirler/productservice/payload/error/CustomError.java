package com.emlakjet.ismaildemirler.productservice.payload.error;

import java.util.Date;

import com.emlakjet.ismaildemirler.productservice.payload.PayloadModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomError implements PayloadModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2331015778244561550L;
	
	private Date timeStamp;
	private String message;
	private String details;
}
