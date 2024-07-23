package com.eventos.api.services.exceptions;

public class IntegrityViolationException extends RuntimeException {

    public IntegrityViolationException(String msg) {
        super(msg);
    }
}
