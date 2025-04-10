package com.example.Login.exceptionhandler;

import org.springframework.security.authentication.BadCredentialsException;

public class WrongPasswordException extends BadCredentialsException {
    public WrongPasswordException() {
        super("Wrong Password");
    }
}
