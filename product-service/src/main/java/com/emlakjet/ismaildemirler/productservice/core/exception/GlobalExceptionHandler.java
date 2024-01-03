package com.emlakjet.ismaildemirler.productservice.core.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emlakjet.ismaildemirler.productservice.payload.ServiceResponse;
import com.emlakjet.ismaildemirler.productservice.util.exception.CustomException;
import com.emlakjet.ismaildemirler.productservice.util.exception.DataNotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ServiceResponse> customException(CustomException e) {
		return new ResponseEntity<ServiceResponse>(
				ServiceResponse.builder().message(e.getMessage()).error(e.getMessage()).data(null).success(false).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ServiceResponse> dataNotFoundException(DataNotFoundException e) {
		return new ResponseEntity<ServiceResponse>(
				ServiceResponse.builder().message(e.getMessage()).error(e.getMessage()).data(null).success(false).build(),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> runtimeException(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				ServiceResponse.builder().message("Teknik bir hata ile karşılaşıldı!").error(e.getMessage()).data(null).success(false).build());
	}
}
