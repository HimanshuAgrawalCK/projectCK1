package com.example.Login.aws;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;

@Configuration
public class AwsConfig {

    @Bean
    public Region getRegion(){
        return Region.US_EAST_1;
    }

    @Bean
    public StsClient stsClient(Region region){
        return StsClient.builder().region(region).build();
    }
}
