package com.example.Login.repository;

import com.example.Login.dto.UserSummary;
import com.example.Login.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>
{
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findAll();
    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, r.role AS roleName " +
            "FROM User u JOIN u.role r")
    Page<UserSummary> findAllUserSummaries(Pageable pageable);
}
