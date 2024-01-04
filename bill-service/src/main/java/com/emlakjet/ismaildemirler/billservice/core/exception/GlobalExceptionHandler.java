package com.emlakjet.ismaildemirler.billservice.core.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emlakjet.ismaildemirler.billservice.core.ServiceResponse;
import com.emlakjet.ismaildemirler.billservice.util.exception.CustomException;
import com.emlakjet.ismaildemirler.billservice.util.exception.LimitException;
import com.emlakjet.ismaildemirler.billservice.util.exception.ResourceNotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> customException(CustomException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResponse.builder()
				.message(ex.getMessage()).error(ex.getMessage()).data(null).success(false).build());
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> usernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceResponse.builder().message(ex.getMessage())
				.error(ex.getMessage()).data(null).success(false).build());
	}

	@ExceptionHandler(LimitException.class)
	public ResponseEntity<Object> limitException(LimitException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ServiceResponse.builder().message(ex.getMessage())
				.error(ex.getMessage()).data(null).success(false).build());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> badcredentialsException(BadCredentialsException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ServiceResponse.builder().message("Kullanıcı adı veya şifreniz yanlış! Lütfen tekrar deneyiniz.")
						.data(null).success(false).build());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceResponse.builder().message(ex.getMessage())
				.error(ex.getMessage()).data(null).success(false).build());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exceptionHandler(Exception ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResponse.builder()
				.message("Teknik bir hata ile karşılaşıldı!").error(ex.getMessage()).data(null).success(false).build());
	}
}
