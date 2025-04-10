package com.example.Login.service;


import com.example.Login.entity.ERole;
import com.example.Login.entity.Role;
import com.example.Login.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public List<ERole> getAllRoles(){
        List<ERole> roles = roleRepository.findAllRoles();
        return roles;
    }
}
