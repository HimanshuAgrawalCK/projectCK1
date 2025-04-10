package com.example.Login.dto;


import com.example.Login.entity.ERole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {

    private Long id;
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private ERole role;
}
