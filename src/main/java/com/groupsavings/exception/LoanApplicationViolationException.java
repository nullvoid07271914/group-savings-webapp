package com.groupsavings.exception;

public class LoanApplicationViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoanApplicationViolationException(String message) {
        super(message);
    }
}
