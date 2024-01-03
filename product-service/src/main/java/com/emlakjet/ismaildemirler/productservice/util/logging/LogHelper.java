package com.emlakjet.ismaildemirler.productservice.util.logging;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.emlakjet.ismaildemirler.productservice.core.exception.CustomError;
import com.emlakjet.ismaildemirler.productservice.payload.logging.LoggingResponse;

import static com.emlakjet.ismaildemirler.productservice.util.StreamUtils.getStringFromStream;

@Component
public class LogHelper {
	
	public void logError(Logger logger, String className, String methodName, String args, Throwable exception) {		
		var customErrorMessage = CustomError.builder()
				.timeStamp(new Date())
				.message(exception.getMessage())
				.details(getStringFromStream(exception.getStackTrace()))
				.build();
		 
		log(logger, className, methodName, args, customErrorMessage, 0L, LogLevel.ERROR);
    }
	
	public void logDebug(Logger logger, String className, String methodName, String args, Object result, long elapsedTime) {	
		log(logger, className, methodName, args, result, elapsedTime, LogLevel.DEBUG);
	}
	
	private void log(Logger logger, String className, String methodName, String args, Object result, long elapsedTime, LogLevel level) {		
		LoggingResponse response = LoggingResponse.builder()
			.className(className)
			.methodName(methodName)
			.methodArgs(args)
			.authEmail("")
			.elapsedTimeInMicros(elapsedTime > 0 ? TimeUnit.NANOSECONDS.toMicros(elapsedTime) : null)
			.elapsedTimeInMillis(elapsedTime > 0 ? TimeUnit.NANOSECONDS.toMillis(elapsedTime) : null)
			.result(result)
			.build();	

		log(logger, level, response);
	}
	
	public void log(Logger logger, LogLevel level, LoggingResponse response) {
		switch (level) {
			case ERROR: {
				logger.error("Logging Error : {}", response);
				break;
			}
			case DEBUG: {
				logger.debug("Logging Debug : {}", response);
				break;
			}
			case INFO: {
				logger.info("Logging Info : {}", response);
				break;
			}
			case TRACE: {
				logger.trace("Logging Trace : {}", response);
				break;
			}
			default: {
				break;
			}
		}
	}
}