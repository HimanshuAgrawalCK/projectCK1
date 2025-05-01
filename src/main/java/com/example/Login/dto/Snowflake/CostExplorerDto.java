package com.example.Login.dto.Snowflake;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostExplorerDto {
    @NotNull(message = "Group By Name cannot be empty")
    @NotBlank(message = "Group by cannot be blank")
    private String groupByName;
    private List<Map<String,Object>> filters;
    @NotNull(message = "accountId cannot be empty")
    @NotBlank(message = "accountId cannot be blank")
    private String accountId;

    private Long startMonth;
    private Long startYear;
    private Long endMonth;
    private Long endYear;
}
