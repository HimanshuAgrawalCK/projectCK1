package com.example.Login.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.rds.RdsClient;

@Component
@RequiredArgsConstructor
public class AwsClientFactory {
    public Ec2Client createEc2Client(AwsSessionCredentials sessionCredentials, String region) {

        return Ec2Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                .region(Region.of(region))
                .build();

    }

    public RdsClient createRdsClient(AwsSessionCredentials sessionCredentials, String region) {
        return RdsClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                .region(Region.of(region))
                .build();

    }

    public AutoScalingClient createAsgClient(AwsSessionCredentials sessionCredentials, String region) {
        return AutoScalingClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                .region(Region.of(region))
                .build();
    }
}
