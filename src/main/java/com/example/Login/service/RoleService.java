package com.example.Login.service;


import com.example.Login.entity.ERole;
import com.example.Login.entity.Role;
import com.example.Login.repository.RoleRepository;
import com.example.Login.service.serviceInterfaces.RoleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements RoleInterface {
    @Autowired
    RoleRepository roleRepository;
    public List<ERole> getAllRoles(){
        return roleRepository.findAllRoles();
    }
}
