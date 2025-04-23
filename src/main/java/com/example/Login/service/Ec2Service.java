package com.example.Login.service;

import com.example.Login.aws.AwsClientFactory;
import com.example.Login.aws.AwsConfig;
import com.example.Login.dto.AwsEc2InstanceDetails;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.exceptionhandler.AccountDoesNotExists;
import com.example.Login.exceptionhandler.AwsServiceException;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.service.serviceInterfaces.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;
import software.amazon.awssdk.services.sts.model.StsException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Ec2Service implements AwsService {
    private final StsAssumeRoleService stsAssumeRoleService;
    private final AwsClientFactory awsClientFactory;
    private final AwsConfig awsConfig;
    private final AwsAccountsRepository accountsRepository;

    public List<AwsEc2InstanceDetails> listInstances(Long accountId) {
        List<AwsEc2InstanceDetails> awsEc2InstanceDetailsList = new ArrayList<>();
        try {
            AwsAccounts account = accountsRepository.findByAccountId(accountId)
                    .orElseThrow(() -> new AccountDoesNotExists("No Account Exists"));

            AwsSessionCredentials sessionCredentials = stsAssumeRoleService.assumeRole(account.getArn());
            Ec2Client ec2Client = awsClientFactory.createEc2Client(sessionCredentials, "us-east-1");

            DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
            DescribeInstancesResponse response = ec2Client.describeInstances(request);

            for (Reservation reservation : response.reservations()) {
                reservation.instances().forEach(instance -> {
                    AwsEc2InstanceDetails awsEc2InstanceDetails = new AwsEc2InstanceDetails();

                    String name = instance.tags().stream()
                            .filter(tag -> tag.key().equalsIgnoreCase("Name"))
                            .map(Tag::value)
                            .findFirst()
                            .orElse("UnNamed");

                    awsEc2InstanceDetails.setInstanceId(instance.instanceId());
                    awsEc2InstanceDetails.setInstanceName(name);
                    awsEc2InstanceDetails.setRegion(ec2Client.serviceClientConfiguration().region().toString());
                    awsEc2InstanceDetails.setStatus(instance.state().nameAsString());
                    awsEc2InstanceDetailsList.add(awsEc2InstanceDetails);
                });
            }
        } catch (AccountDoesNotExists e) {
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

        } catch (Ec2Exception e) {
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

        return awsEc2InstanceDetailsList;
    }
}
