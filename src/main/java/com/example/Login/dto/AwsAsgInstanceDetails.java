package com.example.Login.dto;


import lombok.Data;

@Data
public class AwsAsgInstanceDetails {
    private String name;
    private int desiredCapacity;
    private int minSize;
    private int maxSize;
    private String region;
    private String resourceId;
    private String status;

}
