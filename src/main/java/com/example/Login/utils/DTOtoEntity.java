package com.example.Login.utils;

import com.example.Login.dto.AwsDTO;
import com.example.Login.dto.UserDTO;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.Role;
import com.example.Login.entity.User;
import com.example.Login.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DTOtoEntity {


    public static User map(UserDTO userDTO){
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public static AwsAccounts map(AwsDTO awsDTO){
        AwsAccounts awsAccounts = new AwsAccounts();
        awsAccounts.setAccountName(awsDTO.getAccountName());
        awsAccounts.setArn(awsDTO.getArn());
        awsAccounts.setRoleName(awsDTO.getRoleName());
        awsAccounts.setAccountId(awsDTO.getAccountId());
        return awsAccounts;
    }


}
