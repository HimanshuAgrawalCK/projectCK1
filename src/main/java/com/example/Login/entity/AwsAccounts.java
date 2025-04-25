package com.example.Login.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="accounts")
public class AwsAccounts
{

    @Column(name = "account_id")
    @Id
    private Long accountId;

    @Column(name="account_name")
    private String accountName;

    private String arn;
}
