package com.example.Login.controller;


import com.example.Login.dto.AwsDTO;
import com.example.Login.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aws")
public class AwsController {

    @Autowired
    AccountsService accountsService;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addAwsAccount(@RequestBody AwsDTO awsDTO){
        return ResponseEntity.ok(accountsService.addAwsAccount(awsDTO));
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_READONLY')")
    @GetMapping
    public ResponseEntity<?> getAllAccounts(@RequestParam(required = false) Long id){
        return ResponseEntity.ok(accountsService.getAccountSummary(id));
    }
}
