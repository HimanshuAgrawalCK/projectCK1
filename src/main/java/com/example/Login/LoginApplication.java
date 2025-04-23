package com.example.Login;

//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.ec2.AmazonEC2;
//import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.List;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class LoginApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);


//		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIAQZFTAVPYAG7WXK5S","NWQ2arqLax3O4f4uMDKIr2z9UzoD4zXjKaJMTXur");
//		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard().build();
////		List<?> ec2_instance =
	}


}