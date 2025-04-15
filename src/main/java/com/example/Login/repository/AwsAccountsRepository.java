package com.example.Login.repository;

import com.example.Login.dto.AccountSummary;
import com.example.Login.entity.AwsAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AwsAccountsRepository extends JpaRepository<AwsAccounts, Long> {
    Boolean existsByArn(String arn);

    Optional<AwsAccounts> findByArn(String arn);

    @Query(value = "SELECT aws.accountId AS accountId," +
            "aws.accountName AS accountName" +
            "  FROM AwsAccounts aws")
    List<AccountSummary> getAccountSummary();

    Optional<AwsAccounts> findByAccountId(Long id);

}
