package com.example.Login.dto;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class LoginReponse
{
    private String token;
    private String type="Bearer";
    private Long id;
    private String email;
    private String message;
    private HttpStatus status;
}
