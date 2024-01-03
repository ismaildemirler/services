package com.emlakjet.ismaildemirler.billservice.core.exception;

import java.util.Date;

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
public class CustomError {
	private Date timeStamp;
	private String message;
	private String details;
}
