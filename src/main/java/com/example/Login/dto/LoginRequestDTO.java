package com.example.Login.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO
{
    @NotBlank(message = "Email cannot be blank")
    private String email;


    @NotBlank(message = "Password cannot be blank")
    private String password;
}
