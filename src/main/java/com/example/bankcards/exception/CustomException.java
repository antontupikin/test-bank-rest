package com.example.bankcards.exception;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
    private final String message;
    private final Integer code;
    private final Object[] args;

    public CustomException(String message, Integer code) {
        super(message);
        this.message = message;
        this.args = null;
        this.code = code;
    }

    public CustomException(String message, Integer code, Object... args) {
        super(message);
        this.message = message;
        this.args = args;
        this.code = code;
    }

    public CustomException(String message, Integer code, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.args = null;
        this.code = code;
    }

    public abstract CustomException createCustomException(String message);
}
