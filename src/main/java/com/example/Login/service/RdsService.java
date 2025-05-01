package com.example.Login.service;

import com.example.Login.aws.AwsClientFactory;
import com.example.Login.dto.AwsRdsInstanceDetails;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.User;
import com.example.Login.exceptionhandler.*;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.service.serviceInterfaces.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;

import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesRequest;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;
import software.amazon.awssdk.services.rds.model.RdsException;
import software.amazon.awssdk.services.sts.model.StsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RdsService implements AwsService {
    private final StsAssumeRoleService stsAssumeRoleService;
    private final AwsClientFactory awsClientFactory;
    private final AwsAccountsRepository accountsRepository;
    private final UserRepository userRepository;

    public List<AwsRdsInstanceDetails> listInstances(Long accountId) {
        List<AwsRdsInstanceDetails> rdsDetailsList = new ArrayList<>();
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
            AwsSessionCredentials sessionCredentials = stsAssumeRoleService.assumeRole(account.getArn());

            RdsClient rdsClient = awsClientFactory.createRdsClient(sessionCredentials, "us-east-1");

            DescribeDbInstancesResponse response = rdsClient.describeDBInstances(DescribeDbInstancesRequest.builder().build());

            for (DBInstance dbInstance : response.dbInstances()) {
                AwsRdsInstanceDetails details = new AwsRdsInstanceDetails();
                details.setResourceName(dbInstance.dbInstanceIdentifier());
                details.setEngine(dbInstance.engine());

                details.setResourceId(dbInstance.dbInstanceArn());
                details.setRegion(rdsClient.serviceClientConfiguration().region().toString());
                details.setStatus(dbInstance.dbInstanceStatus());

                rdsDetailsList.add(details);
            }
        } catch (AccountDoesNotExists e) {
            throw e; // Already a custom exception, no need to wrap

        } catch (StsException e) {
            String errorCode = e.awsErrorDetails().errorCode();
            String errorMessage = e.awsErrorDetails().errorMessage();

            if ("AccessDenied".equalsIgnoreCase(errorCode)) {
                throw new AwsServiceException("Unauthorized to assume the selected arn ");
            } else if (errorCode.contains("ValidationError")) {
                throw new AwsServiceException("Invalid ARN ");
            } else {
                throw new AwsServiceException("Unable to connect ");
            }

        } catch (RdsException e) {
            String errorCode = e.awsErrorDetails().errorCode();
            String errorMessage = e.awsErrorDetails().errorMessage();

            if ("UnauthorizedOperation".equalsIgnoreCase(errorCode)) {
                throw new AwsServiceException("Unable to access the resource");
            } else {
                throw new AwsServiceException("RDS Exception");
            }
        } catch (CustomSdkException e) {
            throw new CustomSdkException("Unexpected SDK Exception");
        }
        return rdsDetailsList;
    }
}
