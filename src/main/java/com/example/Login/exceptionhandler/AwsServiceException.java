package com.example.Login.exceptionhandler;

public class AwsServiceException extends RuntimeException {
    public AwsServiceException(String message) {
        super(message);
    }

    public AwsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
