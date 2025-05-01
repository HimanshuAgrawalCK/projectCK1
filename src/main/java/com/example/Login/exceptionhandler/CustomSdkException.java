package com.example.Login.exceptionhandler;

public class CustomSdkException extends RuntimeException {
    public CustomSdkException(String message) {
        super(message);
    }
}
