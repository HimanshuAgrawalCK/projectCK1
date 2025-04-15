package com.example.Login.controller;


import com.example.Login.dto.AwsDTO;
import com.example.Login.service.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aws")
public class AwsController {

    @Autowired
    AwsService awsService;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addAwsAccount(@RequestBody AwsDTO awsDTO){
        return ResponseEntity.ok(awsService.addAwsAccount(awsDTO));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllAccounts(@RequestParam(required = false) Long id){
        return ResponseEntity.ok(awsService.getAccountSummary(id));
    }
}
