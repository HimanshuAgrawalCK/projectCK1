package com.example.Login.controller;

import com.example.Login.dto.JwtResponse;
import com.example.Login.dto.LoginRequestDTO;
import com.example.Login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/cloudbalance")
public class LoginController
{

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER','ROLE_READONLY')")
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token){
        log.info("TOKEN : " + token);
        userService.logout(token);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }

}
