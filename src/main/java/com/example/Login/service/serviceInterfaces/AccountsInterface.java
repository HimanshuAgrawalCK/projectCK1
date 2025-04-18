package com.example.Login.service.serviceInterfaces;

import com.example.Login.dto.AccountSummary;
import com.example.Login.dto.AwsDTO;

import java.util.List;

public interface AccountsInterface {

    AwsDTO addAwsAccount(AwsDTO awsDTO);

    List<AccountSummary> getAccountSummary(Long id);

}
