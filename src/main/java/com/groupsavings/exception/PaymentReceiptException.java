package com.groupsavings.exception;

public class PaymentReceiptException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PaymentReceiptException(String message) {
		super(message);
	}
}
