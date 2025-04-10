package com.example.Login.exceptionhandler;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {
        super("User already exists");

    }
}
