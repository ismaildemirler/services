package com.emlakjet.ismaildemirler.productservice.payload.logging;

import com.emlakjet.ismaildemirler.productservice.payload.PayloadModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggingResponse implements PayloadModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6443719858285085388L;
	
	private String className;
	private String methodName;
	private String methodArgs;
	private String authEmail;
	private Long elapsedTimeInMillis;
	private Long elapsedTimeInMicros;
	private Object result;
	
	@Override
	public String toString() {
		try {
			return "{" + 
				   "className='" + className + '\'' + 
				   ", methodName='" + methodName + '\'' + 
				   ", methodArgs='" + methodArgs + '\'' +
				   ", authEmail='" + authEmail + '\'' + 
				   ", elapsedTimeInMillis=" + elapsedTimeInMillis + '\'' + 
				   ", elapsedTimeInMicros=" + elapsedTimeInMicros + '\'' + 
				   ", result=" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this.result) + 
				   "}";
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}