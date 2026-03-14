package com.groupsavings.exception;

public class LoanUnSuccessfulTransactionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoanUnSuccessfulTransactionException(String message) {
		super(message);
	}
}
