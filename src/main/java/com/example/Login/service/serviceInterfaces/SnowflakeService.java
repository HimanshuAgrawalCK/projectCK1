package com.example.Login.service.serviceInterfaces;

import com.example.Login.dto.Snowflake.AccountCostCount;

import java.util.List;
import java.util.Map;

public interface SnowflakeService
{
    List<AccountCostCount> getCountGroupById();
}
