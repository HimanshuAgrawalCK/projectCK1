package com.example.Login.exceptionhandler;

public class AccountNotAssociated extends RuntimeException {
    public AccountNotAssociated(String message) {
        super(message);
    }
}
