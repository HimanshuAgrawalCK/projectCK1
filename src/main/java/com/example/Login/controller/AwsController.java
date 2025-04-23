package com.example.Login.controller;


import com.example.Login.dto.AwsDTO;
import com.example.Login.service.AccountsService;
import com.example.Login.service.AsgService;
import com.example.Login.service.Ec2Service;
import com.example.Login.service.RdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aws")
public class AwsController {
    @Autowired
    Ec2Service ec2Service;

    @Autowired
    AccountsService accountsService;

    @Autowired
    RdsService rdsService;

    @Autowired
    AsgService asgService;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addAwsAccount(@RequestBody AwsDTO awsDTO){
        return ResponseEntity.ok(accountsService.addAwsAccount(awsDTO));
    }


//    @PreAuthorize("hasAnyAuthority('ROLE)
    @GetMapping
    public ResponseEntity<?> getAllAccounts(@RequestParam(required = false) Long id){
        return ResponseEntity.ok(accountsService.getAccountSummary(id));
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_READONLY')")
    @GetMapping("/ec2instances")
    public ResponseEntity<?> getEc2Details(@RequestParam Long accountId){
        return ResponseEntity.ok(ec2Service.listInstances(accountId));
    }

    @GetMapping("/rdsinstances")
    public ResponseEntity<?> getRdsDetails(@RequestParam Long accountId){
        return ResponseEntity.ok(rdsService.listInstances(accountId));
    }

    @GetMapping("/asginstances")
    public ResponseEntity<?> getAsgDetails(@RequestParam Long accountId){
        return ResponseEntity.ok(asgService.listInstances(accountId));
    }


}
