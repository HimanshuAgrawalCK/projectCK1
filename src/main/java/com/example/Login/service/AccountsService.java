package com.example.Login.service;


import com.example.Login.dto.AccountSummary;
import com.example.Login.dto.AccountSummaryDTO;
import com.example.Login.dto.AwsDTO;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.User;
import com.example.Login.exceptionhandler.AccountAlreadyExists;
import com.example.Login.exceptionhandler.AwsArnAlreadyExists;
import com.example.Login.exceptionhandler.UserNotFoundException;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.security.jwt.JWTService;
import com.example.Login.utils.DTOtoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.Login.service.serviceInterfaces.AccountsInterface;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountsService implements AccountsInterface {

    @Autowired
    private DTOtoEntity dtOtoEntity;

    @Autowired
    private AwsAccountsRepository awsAccountsRepository;

    @Autowired
    private UserRepository userRepository;
//
//    @Autowired
//    private JWTService jwt;

    public AwsDTO addAwsAccount(AwsDTO awsDTO) {
        if (awsAccountsRepository.existsById(awsDTO.getAccountId())) {
            throw new AccountAlreadyExists("Account Already Exists");
        }
        if (awsAccountsRepository.existsByArn(awsDTO.getArn())) {
            throw new AwsArnAlreadyExists("ARN has to be unique");
        }
        AwsAccounts awsAccounts = DTOtoEntity.map(awsDTO);
        log.info("Aws Accounts : ", awsAccounts);
        awsAccountsRepository.save(awsAccounts);
        return awsDTO;
    }


    public List<AccountSummary> getAccountSummary(Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new RuntimeException("No user exists"));
        if(id==0 && !user.getRole().getRole().name().equalsIgnoreCase("customer")){
            return awsAccountsRepository.getAccountSummary();
        }
        user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("No user Found"));
        if (user.getRole().getRole().name().equals("CUSTOMER")) {
            return user.getAwsAccountsList().stream()
                    .map(awsAccounts -> new AccountSummaryDTO(
                            awsAccounts.getAccountId(),
                            awsAccounts.getAccountName()))
                    .collect(Collectors.toList());
        }
        return awsAccountsRepository.getAccountSummary();
    }

//    public AwsDTO getAwsAccount()


}
