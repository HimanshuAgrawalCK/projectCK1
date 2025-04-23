package com.example.Login.service;


import com.example.Login.service.serviceInterfaces.StsAssumeRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;

@Service
@Slf4j
@RequiredArgsConstructor
public class StsAssumeRoleService implements StsAssumeRole {
    private final StsClient stsClient;

    @Override
    public AwsSessionCredentials assumeRole(String roleArn) {
        AssumeRoleRequest request = AssumeRoleRequest.builder()
                .roleArn(roleArn)
                .durationSeconds(3600)
                .roleSessionName("Cloudbalance")
                .build();

        AssumeRoleResponse response = stsClient.assumeRole(request);
        Credentials credentials = response.credentials();

        return AwsSessionCredentials.create(
                credentials.accessKeyId(),
                credentials.secretAccessKey(),
                credentials.sessionToken());
    }
}
