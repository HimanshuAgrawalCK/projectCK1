package com.example.Login.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="accounts")
//@ToString
public class AwsAccounts
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private String accountName;

    private String roleName;

    private String arn;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean isOrphan;
}
