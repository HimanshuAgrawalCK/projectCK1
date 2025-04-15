package com.example.Login.exceptionhandler;

import jakarta.servlet.ServletException;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
