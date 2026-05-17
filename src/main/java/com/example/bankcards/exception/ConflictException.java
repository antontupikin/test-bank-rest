package com.example.bankcards.exception;

public class ConflictException extends CustomException {

    public ConflictException(String message) {
        super(message, 0);
    }

    public ConflictException(String message, Object... args) {
        super(message, 0, args);
    }

    @Override
    public ConflictException createCustomException(String message) {
        return new ConflictException(message);
    }
}