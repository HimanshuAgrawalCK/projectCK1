package com.example.Login.exceptionhandler;


import com.example.Login.dto.ExceptionResponse;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import software.amazon.awssdk.core.exception.SdkException;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> invalidToken(ExpiredJwtException ex){
        log.warn("INVALID TOKEN");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> wrongPassword(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(new ExceptionResponse("Bad Credentials",401));
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<String> userExists(RuntimeException ex){
        log.warn("User already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(WrongRoleException.class)
    public  ResponseEntity<String> wrongRole(RuntimeException ex){
        log.warn("Only Admin can add users");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());

    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> malformedException(RuntimeException ex){
        log.warn("THE token is malfunctioning");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> usernameNotFound(RuntimeException ex){
        log.warn("User Does not exists");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotAssociated.class)
    public ResponseEntity<String> accountNotAssociated(RuntimeException ex){
        log.warn("Account Not associated with current user");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeException(RuntimeException ex){
        log.warn("Unexpected error : {}",ex.getMessage());
        if(ex.getMessage().equalsIgnoreCase("Access Denied")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if(ex.getMessage().equalsIgnoreCase("Data Access Exception")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runtime exception");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runtime exception");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> unauthorizedAccess(RuntimeException e){
        log.warn("You do not have access to this resource");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not Authorized");
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> requestParameterNotMatching(MissingServletRequestParameterException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> methodArgumentTypeException(MethodArgumentTypeMismatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noResourceFound(NoResourceFoundException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accessed resource does not exists");
    }

    @ExceptionHandler(AwsArnAlreadyExists.class)
    public ResponseEntity<String> arnAlreadyExists(RuntimeException ex){
        log.warn("SAME ARN ALREADY EXISTS");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AccountAlreadyExists.class)
    public ResponseEntity<String> accountAlreadyExists(RuntimeException ex){
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AccountDoesNotExists.class)
    public ResponseEntity<String> accountDoesNotExists(RuntimeException ex){
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

//    @ExceptionHandler(AwsServiceException.class)
//    public ResponseEntity<String> handleAwsServiceError(AwsServiceException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        String field = ex.getBindingResult().getFieldErrors().get(0).getField();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(field + " " + message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> httpRequestMethod(HttpRequestMethodNotSupportedException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CustomSdkException.class)
    public ResponseEntity<String> handleSdkException(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> globalException(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unexpected Server Error");
    }
}

