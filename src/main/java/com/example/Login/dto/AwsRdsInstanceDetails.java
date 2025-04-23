package com.example.Login.dto;

import lombok.Data;

@Data
public class AwsRdsInstanceDetails {
    private String resourceId;
    private String resourceName;
    private String engine;
    private String region;
    private String status;
}

