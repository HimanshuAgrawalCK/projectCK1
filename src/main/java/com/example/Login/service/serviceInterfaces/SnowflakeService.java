package com.example.Login.service.serviceInterfaces;

import com.example.Login.dto.Snowflake.AccountCostCount;
import com.example.Login.dto.Snowflake.CostExplorerDto;
import com.example.Login.dto.Snowflake.QueryResponse;
import net.snowflake.client.jdbc.SnowflakeSQLException;

import java.util.List;
import java.util.Map;

public interface SnowflakeService
{
    QueryResponse getSumByGroupAndFilter(CostExplorerDto explorerDto) throws SnowflakeSQLException;
    List<String> getAllColumns();
    List<String> getAllDistinctValues(String columnName);
}
