package com.example.Login.snowflake.repository;

import com.example.Login.dto.Snowflake.AccountCostCount;
import com.example.Login.dto.Snowflake.ColumnDistinctValues;
import com.example.Login.dto.Snowflake.QueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
//@RequiredArgsConstructor
public class SnowflakeRepository {

    private final JdbcTemplate jdbcTemplate;


    public SnowflakeRepository(@Qualifier("snowflakeJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public QueryResponse getSumByGroupAndFilter(String query){
        List<Map<String,Object>> resultList = jdbcTemplate.queryForList(query);

        QueryResponse queryResponse = new QueryResponse();
        queryResponse.setRowNum((long) resultList.size());
        queryResponse.setResult(resultList);
        return queryResponse;
    }



    public List<String> getAllDistinctValues(String columnDBName) {
        String query = "SELECT DISTINCT(" + columnDBName + ") FROM COST_EXPLORER where " + columnDBName +" IS NOT NULL LIMIT 1000";
        return jdbcTemplate.queryForList(query,String.class);
    }
}
