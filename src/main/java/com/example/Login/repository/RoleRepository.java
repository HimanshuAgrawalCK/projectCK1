package com.example.Login.repository;

import com.example.Login.dto.RolesSummary;
import com.example.Login.entity.ERole;
import com.example.Login.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRole(ERole role);

    @Query("SELECT DISTINCT r.role FROM Role r")
    List<ERole> findAllRoles();

}
