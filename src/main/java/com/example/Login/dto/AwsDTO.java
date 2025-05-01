package com.example.Login.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AwsDTO {
    @NotNull(message = "Account Id cannot be null")
    private Long accountId;
    @NotNull(message = "Account name cannot be null")
    @NotBlank(message = "Account Name cannot be blank")
    private String accountName;
    @NotNull(message = "ARN cannot be null")
    private String arn;
}
