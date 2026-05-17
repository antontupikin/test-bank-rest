package com.example.bankcards.exception;

public class ForbiddenException extends CustomException {
    public ForbiddenException(String message) {
        super(message, 0);
    }

    @Override
    public ForbiddenException createCustomException(String message) {
        return new ForbiddenException(message);
    }
}
