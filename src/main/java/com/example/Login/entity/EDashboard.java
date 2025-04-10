package com.example.Login.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EDashboard
{
    USER_MANAGEMENT,
    COST_EXPLORER,
    AWS_SERVICES,
    ONBOARDING;

    @JsonCreator
    public static EDashboard fromString(String value) {
        return EDashboard.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
