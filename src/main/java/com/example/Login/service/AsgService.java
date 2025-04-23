package com.example.Login.service;

import com.example.Login.aws.AwsClientFactory;
import com.example.Login.aws.AwsConfig;
import com.example.Login.dto.AwsAsgInstanceDetails;
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
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;
import software.amazon.awssdk.services.autoscaling.model.AutoScalingGroup;
import software.amazon.awssdk.services.autoscaling.model.DescribeAutoScalingGroupsRequest;
import software.amazon.awssdk.services.autoscaling.model.DescribeAutoScalingGroupsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.sts.model.StsException;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AsgService implements AwsService
{
    private final StsAssumeRoleService assumeRoleService;
    private final AwsClientFactory clientFactory;
    private final AwsAccountsRepository accountsRepository;
    @Override
    public List<AwsAsgInstanceDetails> listInstances(Long accountId) {
        List<AwsAsgInstanceDetails> awsAsgInstanceDetailsList = new ArrayList<>();
        try{

            AwsAccounts account = accountsRepository.findByAccountId(accountId)
                    .orElseThrow(()->new AccountDoesNotExists("No Account Exists"));
            AwsSessionCredentials sessionCredentials = assumeRoleService.assumeRole(account.getArn());
            AutoScalingClient asgClient = clientFactory.createAsgClient(sessionCredentials,"us-east-1");

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
                details.setStatus(group.status());
                awsAsgInstanceDetailsList.add(details);
            }

        }catch (AccountDoesNotExists e) {
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
        return awsAsgInstanceDetailsList;
    }
}
