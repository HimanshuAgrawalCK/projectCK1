package com.example.Login.dto;

import com.example.Login.entity.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@Data
public class JwtResponse
{
    private String message;
    private String name;
    private String token;
    private Long id;
    private String email;
    private String role;
    private List<AccountSummary> accounts;
    private String lastLoginTime;

    public JwtResponse(String name, String token, Long id, String email, String role,
                       String message, List<AccountSummary> accounts, LocalDateTime lastLoginTime){
        this.token=token;
        this.name=name;
        this.id = id;
        this.email=email;
        this.role=role;
        this.message = message;
        this.accounts = accounts;
        this.lastLoginTime = lastLoginTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

    }



}
