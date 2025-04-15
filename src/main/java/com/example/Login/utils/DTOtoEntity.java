package com.example.Login.utils;

import com.example.Login.dto.AwsDTO;
import com.example.Login.dto.UserDTO;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.Role;
import com.example.Login.entity.User;
import com.example.Login.repository.AwsAccountsRepository;
import com.example.Login.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component

public class DTOtoEntity {


    private final AwsAccountsRepository awsAccountsRepository;

    @Autowired
    public DTOtoEntity(AwsAccountsRepository awsRepo) {
        this.awsAccountsRepository = awsRepo; // inject once into static field
    }

    public User map(UserDTO userDTO) {
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public AwsAccounts map(AwsDTO awsDTO) {
        AwsAccounts awsAccounts = new AwsAccounts();
        awsAccounts.setAccountName(awsDTO.getAccountName());
        awsAccounts.setArn(awsDTO.getArn());
        awsAccounts.setRoleName(awsDTO.getRoleName());
        awsAccounts.setAccountId(awsDTO.getAccountId());
        return awsAccounts;
    }

    public UserDTO map(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().getRole());
        userDTO.setAccounts(user.getAwsAccountsList()
                .stream()
                .map(accounts-> accounts.getAccountId())
                .collect(Collectors.toSet()));
        return  userDTO;
    }


}
