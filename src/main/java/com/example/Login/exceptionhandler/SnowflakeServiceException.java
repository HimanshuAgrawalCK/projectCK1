package com.example.Login.exceptionhandler;

public class SnowflakeServiceException extends RuntimeException {
    public SnowflakeServiceException(String message) {
        super(message);
    }
}
