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


    @PreAuthorize("has")

    @PostMapping
    public ResponseEntity<?> addAwsAccount(@RequestBody AwsDTO awsDTO){
        return ResponseEntity.ok(awsService.addAwsAccount(awsDTO));
    }

    @PatchMapping
    public void updateOrphanAccount(@RequestBody AwsDTO awsDTO, @RequestParam Boolean isOrphan){
        awsService.updateOrphanAccount(awsDTO,isOrphan);
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts(){
        return ResponseEntity.ok(awsService.getAccountSummary());
    }
}
