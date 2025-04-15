package com.example.Login.exceptionhandler;


import com.example.Login.dto.ExceptionResponse;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> invalidToken(ExpiredJwtException ex){
        log.warn("INVALID TOKEN");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> wrongPassword(RuntimeException ex){
        log.warn("WRONG PASSWORD");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(new ExceptionResponse("Wrong password exception",401));
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> userExists(RuntimeException ex){
        log.warn("User already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(WrongRoleException.class)
    public  ResponseEntity<?> wrongRole(RuntimeException ex){
        log.warn("Only Admin can add users");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(ex.getMessage(),403));

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> expiredToken(ExpiredJwtException ex){
        log.warn("THE TOKEN IS EXPIRED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> malformedException(RuntimeException ex){
        log.warn("THE token is malfunctioning");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> usernameNotFound(RuntimeException ex){
        log.warn("User Does not exists");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException ex){
        log.warn("Unexpected error : {}",ex.getMessage());
        return ResponseEntity.internalServerError().body("An unexpected error occured");
    }

    @ExceptionHandler(AwsArnAlreadyExists.class)
    public ResponseEntity<?> arnAlreadyExists(RuntimeException ex){
        log.warn("SAME ARN ALREADY EXISTS");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }




}
