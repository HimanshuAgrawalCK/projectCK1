package com.example.Login.service.serviceInterfaces;

import com.example.Login.dto.Snowflake.CostExplorerDto;
import com.example.Login.dto.Snowflake.QueryResponse;
import net.snowflake.client.jdbc.SnowflakeSQLException;

import java.util.List;

public interface SnowflakeService
{
    QueryResponse getSumByGroupAndFilter(CostExplorerDto explorerDto,String token);
    List<String> getAllColumns();
    List<String> getAllDistinctValues(String columnName);
}
