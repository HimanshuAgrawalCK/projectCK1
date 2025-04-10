package com.example.Login.service;


import com.example.Login.dto.AccountSummary;
import com.example.Login.dto.AwsDTO;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.exceptionhandler.AwsArnAlreadyExists;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.utils.DTOtoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwsService {

    @Autowired
    AwsAccountsRepository awsAccountsRepository;

    public AwsDTO addAwsAccount(AwsDTO awsDTO) {
        if(awsAccountsRepository.existsByArn(awsDTO.getArn())){
            throw new AwsArnAlreadyExists("ARN has to be unique");
        }

        AwsAccounts awsAccounts = DTOtoEntity.map(awsDTO);
        System.out.println(awsDTO);
        System.out.println("Aws account is " + awsAccounts);
        awsAccountsRepository.save(awsAccounts);
        return awsDTO;
    }

    public void updateOrphanAccount(AwsDTO awsDTO, boolean isOrphan) {
        AwsAccounts awsAccounts = awsAccountsRepository.findByArn(awsDTO.getArn()).get();
        awsAccounts.setOrphan(isOrphan);
        awsAccountsRepository.save(awsAccounts);
    }

    public List<AccountSummary> getAccountSummary() {
        return awsAccountsRepository.getAccountSummary();
    }

//    public AwsDTO getAwsAccount()




}
