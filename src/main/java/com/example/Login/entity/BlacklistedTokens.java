package com.example.Login.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tokens")
public class BlacklistedTokens {

    @Id
    private String token;

}
