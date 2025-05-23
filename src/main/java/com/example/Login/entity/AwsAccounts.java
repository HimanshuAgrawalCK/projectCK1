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

    private String accountName;

    private String roleName;

    private String arn;
}
