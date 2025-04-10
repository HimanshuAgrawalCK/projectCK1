package com.example.Login.repository;


import com.example.Login.entity.BlacklistedTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedTokens,String> {
    Boolean existsByToken(String token);
}
