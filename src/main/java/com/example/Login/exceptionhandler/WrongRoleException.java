package com.example.Login.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.AuthorizationResult;

public class WrongRoleException extends RuntimeException {
    public WrongRoleException(String message) {
        super(message);
    }

//    public WrongRoleException(AuthorizationDeniedException ex){
//        super(ex.getMessage());
//    }
}
