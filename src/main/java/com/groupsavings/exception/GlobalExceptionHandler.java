package com.groupsavings.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
}
