package com.emlakjet.ismaildemirler.billservice.payload.error;

import java.util.Date;

import com.emlakjet.ismaildemirler.billservice.payload.PayloadModel;

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
	private static final long serialVersionUID = 758550910271424960L;
	
	private Date timeStamp;
	private String message;
	private String details;
}
