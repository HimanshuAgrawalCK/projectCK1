package com.example.Login.exceptionhandler;

public class AwsArnAlreadyExists extends RuntimeException {
    public AwsArnAlreadyExists(String message) {
        super(message);
    }
}
