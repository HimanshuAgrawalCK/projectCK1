package com.example.Login.dto;

import com.example.Login.entity.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
public class JwtResponse
{
    private Integer status;
    private String message;
    private String name;
    private String token;
    private String type="bearer";
    private Long id;
    private String email;
    private String role;

    public JwtResponse(String name, String token, Long id, String email, String role, String message, Integer status){
        this.token=token;
        this.name=name;
        this.id = id;
        this.email=email;
        this.role=role;
        this.message = message;
        this.status = status;
    }



}
