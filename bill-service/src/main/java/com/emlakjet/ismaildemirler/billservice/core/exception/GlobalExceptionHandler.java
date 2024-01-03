package com.emlakjet.ismaildemirler.billservice.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emlakjet.ismaildemirler.billservice.payload.ServiceResponse;
import com.emlakjet.ismaildemirler.billservice.util.exception.CustomException;
import com.emlakjet.ismaildemirler.billservice.util.exception.DataNotFoundException;
import com.emlakjet.ismaildemirler.billservice.util.exception.LimitException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ServiceResponse> exceptionHandler(CustomException e) {
		return new ResponseEntity<ServiceResponse>(
				ServiceResponse.builder().message(e.getMessage()).error(e.getMessage()).data(null).success(false).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(LimitException.class)
	public ResponseEntity<ServiceResponse> exceptionHandler(LimitException e) {
		return new ResponseEntity<ServiceResponse>(
				ServiceResponse.builder().message(e.getMessage()).error(e.getMessage()).data(null).success(false).build(),
				HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ServiceResponse> exceptionHandler(UsernameNotFoundException e) {
		return new ResponseEntity<ServiceResponse>(
				ServiceResponse.builder().message(e.getMessage()).error(e.getMessage()).data(null).success(false).build(),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ServiceResponse> exceptionHandler(DataNotFoundException e) {
		return new ResponseEntity<ServiceResponse>(
				ServiceResponse.builder().message(e.getMessage()).error(e.getMessage()).data(null).success(false).build(),
				HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> exceptionHandler(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				ServiceResponse.builder().message("Teknik bir hata ile karşılaşıldı!").error(e.getMessage()).data(null).success(false).build());
	}
}
