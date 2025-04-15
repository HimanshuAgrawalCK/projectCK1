package com.example.Login.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ERole
{
    ADMIN,
    READONLY,
    CUSTOMER;

    @JsonCreator
    public static ERole fromString(String value) {
        return ERole.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
