package com.example.Login.dto;


import com.example.Login.entity.ERole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
public class UserDTO {

    private Long id;
    @NotBlank(message = "Name is blank")
    @NotNull(message = "Name is Empty")
    private String name;

    @NotBlank(message = "email is blank")
    @NotNull(message = "Email is empty")
    private String email;

    @NotBlank(message = "password is blank")
    @NotNull(message = "Password is empty")
    private String password;

    @NotNull(message = "Role cannot be empty")
    private ERole role;

    private Set<Long> accounts ;
}
