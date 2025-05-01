package com.example.Login.service;

import com.example.Login.aws.AwsClientFactory;
import com.example.Login.dto.AwsAsgInstanceDetails;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.User;
import com.example.Login.exceptionhandler.*;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.service.serviceInterfaces.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;
import software.amazon.awssdk.services.autoscaling.model.AutoScalingGroup;
import software.amazon.awssdk.services.autoscaling.model.DescribeAutoScalingGroupsRequest;
import software.amazon.awssdk.services.autoscaling.model.DescribeAutoScalingGroupsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.sts.model.StsException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class AsgService implements AwsService {
    private final StsAssumeRoleService assumeRoleService;
    private final AwsClientFactory clientFactory;
    private final AwsAccountsRepository accountsRepository;
    private final UserRepository userRepository;


    @Override
    public List<AwsAsgInstanceDetails> listInstances(Long accountId) {
        List<AwsAsgInstanceDetails> awsAsgInstanceDetailsList = new ArrayList<>();


        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(()->new UserNotFoundException("User Does Not exists"));

        if(user.getRole().getRole().name().equalsIgnoreCase("CUSTOMER")){
            Set<AwsAccounts> accounts = user.getAwsAccountsList();
            if(accounts.stream().filter(account-> Objects.equals(account.getAccountId(), accountId))
                    .toList().isEmpty()){
                throw new AccountNotAssociated(accountId + "not associated with current user");

            }
        }
        try {

            AwsAccounts account = accountsRepository.findByAccountId(accountId)
                    .orElseThrow(() -> new AccountDoesNotExists("No Account Exists"));
            AwsSessionCredentials sessionCredentials = assumeRoleService.assumeRole(account.getArn());
            AutoScalingClient asgClient = clientFactory.createAsgClient(sessionCredentials, "us-east-1");

            DescribeAutoScalingGroupsRequest request = DescribeAutoScalingGroupsRequest.builder().build();
            DescribeAutoScalingGroupsResponse response = asgClient.describeAutoScalingGroups(request);


            for (AutoScalingGroup group : response.autoScalingGroups()) {
                AwsAsgInstanceDetails details = new AwsAsgInstanceDetails();
                details.setName(group.autoScalingGroupName());
                details.setDesiredCapacity(group.desiredCapacity());
                details.setMinSize(group.minSize());
                details.setMaxSize(group.maxSize());
                details.setRegion(asgClient.serviceClientConfiguration().region().toString());
                details.setResourceId(group.autoScalingGroupARN());
                details.setStatus(group.status() == null ? "N/A" : group.status());
                awsAsgInstanceDetailsList.add(details);
            }

        } catch (AccountDoesNotExists e) {
            throw e; // Already a custom exception, no need to wrap
        } catch (StsException e) {
            String errorCode = e.awsErrorDetails().errorCode();
            String errorMessage = e.awsErrorDetails().errorMessage();
            log.info("Error Code : " + errorCode + " \n Error Message : " + errorMessage);
            if ("AccessDenied".equalsIgnoreCase(errorCode)) {
                throw new AwsServiceException("Unauthorized to assume the selected arn");
            } else if (errorCode.contains("ValidationError")) {
                throw new AwsServiceException("Invalid ARN ");
            } else {
                throw new AwsServiceException("Unable to connect ");
            }

        } catch (Ec2Exception e) {
            String errorCode = e.awsErrorDetails().errorCode();
            String errorMessage = e.awsErrorDetails().errorMessage();

            if ("UnauthorizedOperation".equalsIgnoreCase(errorCode)) {
                throw new AwsServiceException("Unauthorized to access the resource");
            } else {
                throw new AwsServiceException("Unexpected EC2 error");
            }

        }catch (SdkException e) {
            throw new CustomSdkException("Unexpected SDK Exception");
        }

        return awsAsgInstanceDetailsList;
    }
}
