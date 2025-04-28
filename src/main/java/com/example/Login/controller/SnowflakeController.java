package com.example.Login.controller;


import com.example.Login.dto.Snowflake.AccountCostCount;
import com.example.Login.dto.Snowflake.ColumnDistinctValues;
import com.example.Login.dto.Snowflake.CostExplorerDto;
import com.example.Login.dto.Snowflake.QueryResponse;
import com.example.Login.service.SnowflakeServiceImpl;
import com.example.Login.service.serviceInterfaces.SnowflakeService;
import com.example.Login.utils.SqlQueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/snowflake")
public class SnowflakeController {
    @Autowired
    private SnowflakeServiceImpl snowflakeService;
    @Autowired
    private SqlQueryGenerator queryGenerator;


    @PostMapping("/getSumByGroupAndFilter")
    public ResponseEntity<QueryResponse> getSumByGroupAndFilter(@RequestBody CostExplorerDto explorerDto){
        return ResponseEntity.ok(snowflakeService.getSumByGroupAndFilter(explorerDto));
    }

    @GetMapping("/getAllColumns")
    public ResponseEntity<List<String>> getAllColumns(){
        return ResponseEntity.ok(snowflakeService.getAllColumns());
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_READONLY','ROLE_CUSTOMER)")
    @GetMapping("/getDistinctValuesByColumn")
    public ResponseEntity<List<String>> getAllDistinctValues(@RequestParam String columnName){
        return ResponseEntity.ok(snowflakeService.getAllDistinctValues(columnName));
    }


}
