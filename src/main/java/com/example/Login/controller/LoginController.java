package com.example.Login.controller;

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

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER','READRONLY')")
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token){
        System.out.println(token);
        log.info("TOKEN : " + token);
        userService.logout(token);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT);
    }


    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(userService.UserAuth(loginRequestDTO));
    }

}
