package com.korolko.converter.repository.exception;

public class CurrencyNotFoundRuntimeException extends RuntimeException {

    public CurrencyNotFoundRuntimeException() {
        super();
    }

    public CurrencyNotFoundRuntimeException(String message) {
        super(message);
    }
}
