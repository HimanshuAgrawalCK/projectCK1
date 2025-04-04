package com.example.Login.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})

public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @ManyToOne
    @JoinColumn(name="id")
    private Role role;

}
