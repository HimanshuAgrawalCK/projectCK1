package com.example.Login.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface UserSummary {
    Long getId();
    String getName();
    String getEmail();
    String getRoleName();
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime getLastLoginTime();
}
