package com.groupsavings.exception;

import java.io.Serial;

public class MemberInActiveStatusException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MemberInActiveStatusException(String message) {
        super(message);
    }

}