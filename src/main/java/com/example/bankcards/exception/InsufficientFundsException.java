package com.example.bankcards.exception;

public class InsufficientFundsException extends CustomException {

    public InsufficientFundsException(String message) {
        super(message, 0);
    }

    public InsufficientFundsException(String message, Object... args) {
        super(message, 0, args);
    }

    @Override
    public InsufficientFundsException createCustomException(String message) {
        return new InsufficientFundsException(message);
    }}
