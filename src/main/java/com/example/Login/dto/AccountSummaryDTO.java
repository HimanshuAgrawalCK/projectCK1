package com.example.Login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountSummaryDTO implements AccountSummary{

    private Long accountId;
    private String accountName;
}
