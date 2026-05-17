package com.example.bankcards.exception;

public class NotFoundException extends CustomException {
    public NotFoundException(String message) {
        super(message, 0);
    }

    public NotFoundException(String message, Object... args) {
        super(message, 0, args);
    }

    @Override
    public NotFoundException createCustomException(String message) {
        return new NotFoundException(message);
    }
}