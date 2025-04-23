package com.example.Login.exceptionhandler;

public class AccountDoesNotExists extends RuntimeException {
    public AccountDoesNotExists(String message) {
        super(message);
    }
}
