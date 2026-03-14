package com.groupsavings.exception;

public class RequestInsufficientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestInsufficientException(String message) {
		super(message);
	}
}
