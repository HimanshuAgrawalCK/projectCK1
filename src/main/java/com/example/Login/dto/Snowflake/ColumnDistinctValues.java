package com.example.Login.dto.Snowflake;

import lombok.Data;
import java.util.List;


@Data
public class ColumnDistinctValues
{
    private String columnName;
    private List<Object> distinctValues;
}
