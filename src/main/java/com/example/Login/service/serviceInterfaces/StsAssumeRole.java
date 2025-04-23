package com.example.Login.service.serviceInterfaces;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;

public interface StsAssumeRole {
    public AwsSessionCredentials assumeRole(String roleArn);
}
