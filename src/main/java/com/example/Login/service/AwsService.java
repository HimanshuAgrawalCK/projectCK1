package com.example.Login.service;


import com.example.Login.dto.AccountSummary;
import com.example.Login.dto.AwsDTO;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.ERole;
import com.example.Login.entity.User;
import com.example.Login.exceptionhandler.AwsArnAlreadyExists;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.utils.DTOtoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwsService {

    @Autowired
    DTOtoEntity dtOtoEntity;

    @Autowired
    AwsAccountsRepository awsAccountsRepository;

    @Autowired
    UserRepository userRepository;


    public AwsDTO addAwsAccount(AwsDTO awsDTO) {
        if(awsAccountsRepository.existsByArn(awsDTO.getArn())){
            throw new AwsArnAlreadyExists("ARN has to be unique");
        }
        DTOtoEntity dtOtoEntity = new DTOtoEntity(awsAccountsRepository);
        AwsAccounts awsAccounts = dtOtoEntity.map(awsDTO);
        System.out.println(awsDTO);
        System.out.println("Aws account is " + awsAccounts);
        awsAccountsRepository.save(awsAccounts);
        return awsDTO;
    }


    public List<AccountSummary> getAccountSummary(Long id) {
        if(id==null  || id==0){
            return awsAccountsRepository.getAccountSummary();
        }
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(id).get();
        if(user.getRole().getRole().name().equals("CUSTOMER")){
            List<AccountSummary> accountSummaries = user.getAwsAccountsList().stream()
                    .map(awsAccounts -> new AccountSummary() {
                        @Override
                        public Long getAccountId() {
                            return awsAccounts.getAccountId();
                        }

                        @Override
                        public String getAccountName() {
                            return awsAccounts.getAccountName();
                        }
                    })
                    .collect(Collectors.toList());
            return accountSummaries;
        }

        return awsAccountsRepository.getAccountSummary();
    }

//    public AwsDTO getAwsAccount()




}
