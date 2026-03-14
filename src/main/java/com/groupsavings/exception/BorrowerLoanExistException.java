package com.groupsavings.exception;

public class BorrowerLoanExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BorrowerLoanExistException(String message) {
		super(message);
	}
}
