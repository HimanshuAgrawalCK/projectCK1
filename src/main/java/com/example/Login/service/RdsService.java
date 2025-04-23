package com.example.Login.service;

import com.example.Login.aws.AwsClientFactory;
import com.example.Login.aws.AwsConfig;
import com.example.Login.dto.AwsRdsInstanceDetails;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.exceptionhandler.AccountDoesNotExists;
import com.example.Login.exceptionhandler.AwsServiceException;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.service.serviceInterfaces.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesRequest;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;
import software.amazon.awssdk.services.rds.model.RdsException;
import software.amazon.awssdk.services.sts.model.StsException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RdsService implements AwsService {
    private final StsAssumeRoleService stsAssumeRoleService;
    private final AwsClientFactory awsClientFactory;
    private final AwsConfig awsConfig;
    private final AwsAccountsRepository accountsRepository;
    public List<AwsRdsInstanceDetails> listInstances(Long accountId) {
        List<AwsRdsInstanceDetails> rdsDetailsList = new ArrayList<>();
        try{


            AwsAccounts account = accountsRepository.findByAccountId(accountId)
                    .orElseThrow(()->new AccountDoesNotExists("No Account Exists"));
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
        }
        catch (AccountDoesNotExists e) {
            throw e; // Already a custom exception, no need to wrap

        } catch (StsException e) {
            String errorCode = e.awsErrorDetails().errorCode();
            String errorMessage = e.awsErrorDetails().errorMessage();

            if ("AccessDenied".equalsIgnoreCase(errorCode)) {
                throw new AwsServiceException("Unauthorized to assume the role: " + errorMessage, e);
            } else if ("MalformedPolicyDocument".equalsIgnoreCase(errorCode)
                    || errorMessage.toLowerCase().contains("arn")) {
                throw new AwsServiceException("Invalid ARN: " + errorMessage, e);
            } else {
                throw new AwsServiceException("STS Exception: " + errorMessage, e);
            }

        } catch (RdsException e) {
            String errorCode = e.awsErrorDetails().errorCode();
            String errorMessage = e.awsErrorDetails().errorMessage();

            if ("UnauthorizedOperation".equalsIgnoreCase(errorCode)) {
                throw new AwsServiceException("Unauthorized EC2 operation: " + errorMessage, e);
            } else {
                throw new AwsServiceException("EC2 Exception: " + errorMessage, e);
            }

        } catch (SdkClientException | SdkServiceException e) {
            throw new AwsServiceException("AWS SDK Client or Service exception: " + e.getMessage(), e);
        }catch (SdkException e){
            throw new AwsServiceException("Aws SDK Exception : "+ e.getMessage(),e);
        }
        catch (Exception e) {
            throw new AwsServiceException("Unexpected error while listing EC2 instances.", e);
        }
        return rdsDetailsList;
    }
}
