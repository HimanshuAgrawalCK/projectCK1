package com.example.Login.utils;

import com.example.Login.dto.AwsDTO;
import com.example.Login.dto.UserDTO;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component

public class DTOtoEntity {

    public static User map(UserDTO userDTO) {
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public static AwsAccounts map(AwsDTO awsDTO) {
        AwsAccounts awsAccounts = new AwsAccounts();
        awsAccounts.setAccountName(awsDTO.getAccountName());
        awsAccounts.setArn(awsDTO.getArn());
        awsAccounts.setAccountId(awsDTO.getAccountId());
        return awsAccounts;
    }

    public static UserDTO map(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().getRole());

        Set<Long> accounts = user.getAwsAccountsList() != null ?
                user.getAwsAccountsList()
                        .stream()
                        .map(AwsAccounts::getAccountId)
                        .collect(Collectors.toSet()) : null;

        userDTO.setAccounts(accounts);
        return userDTO;
    }


}
