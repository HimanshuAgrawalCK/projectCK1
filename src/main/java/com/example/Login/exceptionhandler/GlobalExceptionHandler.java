package com.example.Login.exceptionhandler;


import com.example.Login.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> wrongPassword(RuntimeException ex){
        log.warn("WRONG PASSWORD");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(new ExceptionResponse("Wrong password exception",401));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> userExists(RuntimeException ex){
        log.warn("User already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(WrongRoleException.class)
    public  ResponseEntity<?> wrongRole(RuntimeException ex){
        log.warn("Only Admin can add users");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(ex.getMessage(),403));

    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> expiredToken(RuntimeException ex){
        log.warn("THE TOKEN IS EXPIRED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ExceptionResponse> malformedException(RuntimeException ex){
        log.warn("THE token is malfunctioning");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body
                (new ExceptionResponse
                        (ex.getMessage(), 403));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> usernameNotFound(RuntimeException ex){
        log.warn("User Does not exists");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(ex.getMessage(),401));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
