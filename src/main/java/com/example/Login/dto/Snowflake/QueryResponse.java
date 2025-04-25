package com.example.Login.dto.Snowflake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse {

    private Long rowNum;
    private List<Map<String,Object>> result;

}
