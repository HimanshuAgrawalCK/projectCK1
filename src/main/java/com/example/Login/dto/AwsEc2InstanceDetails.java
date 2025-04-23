package com.example.Login.dto;


import lombok.Data;

@Data
public class AwsEc2InstanceDetails {
    private String instanceId;
    private String region;
    private String status;
    private String instanceName;
}
