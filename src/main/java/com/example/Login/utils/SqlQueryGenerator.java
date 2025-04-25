package com.example.Login.utils;

import com.example.Login.dto.Snowflake.CostExplorerDto;
import com.example.Login.dto.Snowflake.CostExplorerColumns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SqlQueryGenerator {
    public static String queryGenerator(CostExplorerDto costExplorerDto) {
        StringBuilder query = new StringBuilder("SELECT ");

        // Group by column
        CostExplorerColumns groupByColumn = CostExplorerColumns.fromDisplayName(costExplorerDto.getGroupByName());
        query.append(groupByColumn.getDbColumnName()).append(", ");

        // Add month-year column for grouping
        query.append("TO_CHAR(USAGESTARTDATE, 'MM-YYYY') AS USAGE_MONTH, ");

        query.append("SUM(LINEITEM_USAGEAMOUNT) AS TOTAL_AMOUNT ");
        query.append("FROM COST_EXPLORER ");
        List<Map<String, Object>> filters = costExplorerDto.getFilters();
        boolean hasFilters = filters != null && !filters.isEmpty();
        if (hasFilters) {
            query.append("WHERE ");
            for (int i = 0; i < filters.size(); i++) {
                Map<String, Object> filter = filters.get(i);
                String columnName = (String) filter.get("column");
                String value = (String) filter.get("value");

                String column = CostExplorerColumns.fromDisplayName(columnName).getDbColumnName();
                query.append(column).append(" = '").append(value).append("'");
                if (i < filters.size() - 1) {
                    query.append(" AND ");
                }
            }
        }
        if (costExplorerDto.getStartYear() != null && costExplorerDto.getStartMonth() != null
                && costExplorerDto.getEndYear() != null && costExplorerDto.getEndMonth() != null) {
            if (!hasFilters) query.append("WHERE ");
            else query.append(" AND ");

            query.append("USAGESTARTDATE BETWEEN TO_TIMESTAMP('")
                    .append(costExplorerDto.getStartYear()).append("-").append(costExplorerDto.getStartMonth()).append("-01') ")
                    .append("AND TO_TIMESTAMP('")
                    .append(costExplorerDto.getEndYear()).append("-").append(costExplorerDto.getEndMonth()).append("-01')");
        }
        query.append(" GROUP BY ").append(groupByColumn.getDbColumnName()).append(", TO_CHAR(USAGESTARTDATE, 'MM-YYYY')");
        query.append(" ORDER BY TOTAL_AMOUNT DESC");
        query.append(" LIMIT 1000");
        log.info("SQL QUERY : " + query);
        return query.toString();
    }

}
