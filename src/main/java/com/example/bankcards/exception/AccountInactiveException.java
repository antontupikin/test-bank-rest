package com.example.bankcards.exception;

public class AccountInactiveException extends CustomException {
    public AccountInactiveException(String message) {
        super(message, 0);
    }

    public AccountInactiveException(String message, Object... args) {
        super(message, 0, args);
    }

    @Override
    public AccountInactiveException createCustomException(String message) {
        return new AccountInactiveException(message);
    }
}
