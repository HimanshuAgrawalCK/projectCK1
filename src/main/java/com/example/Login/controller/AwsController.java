package com.example.Login.controller;


import com.example.Login.dto.AwsDTO;
import com.example.Login.service.AccountsService;
import com.example.Login.service.AsgService;
import com.example.Login.service.Ec2Service;
import com.example.Login.service.RdsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/aws")
public class AwsController {

    private final Ec2Service ec2Service;
    private final AccountsService accountsService;
    private final RdsService rdsService;
    private final AsgService asgService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addAwsAccount(@RequestBody @Valid AwsDTO awsDTO){
        return ResponseEntity.ok(accountsService.addAwsAccount(awsDTO));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER','ROLE_READONLY')")
    @GetMapping
    public ResponseEntity<?> getAllAccounts(@RequestParam(required = false) Long id){
        return ResponseEntity.ok(accountsService.getAccountSummary(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER','ROLE_READONLY')")
    @GetMapping("/ec2instances")
    public ResponseEntity<?> getEc2Details(@RequestParam Long accountId){
        return ResponseEntity.ok(ec2Service.listInstances(accountId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_READONLY','ROLE_CUSTOMER')")
    @GetMapping("/rdsinstances")
    public ResponseEntity<?> getRdsDetails(@RequestParam Long accountId){
        return ResponseEntity.ok(rdsService.listInstances(accountId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_READONLY','ROLE_CUSTOMER')")
    @GetMapping("/asginstances")
    public ResponseEntity<?> getAsgDetails(@RequestParam Long accountId){
        return ResponseEntity.ok(asgService.listInstances(accountId));
    }


}
