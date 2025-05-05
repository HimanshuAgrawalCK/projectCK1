package com.example.Login.utils;

import com.example.Login.dto.Snowflake.CostExplorerDto;
import com.example.Login.enums.CostExplorerColumns;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class SqlQueryGenerator {

    public static String queryGenerator(CostExplorerDto costExplorerDto, String limit) {
        StringBuilder query = new StringBuilder("SELECT ");

        // Group by column
        CostExplorerColumns groupByColumn = CostExplorerColumns.fromDisplayName(costExplorerDto.getGroupByName());
        String groupByDbColumn = groupByColumn.getDbColumnName();
        String groupByDisplayName = groupByColumn.getDisplayName();

        query.append(groupByDbColumn)
                .append(" AS \"").append(groupByDisplayName).append("\", ")
                .append("TO_CHAR(USAGESTARTDATE, 'MM-YYYY') AS USAGE_MONTH, ")
                .append("SUM(LINEITEM_USAGEAMOUNT) AS TOTAL_AMOUNT ")
                .append("FROM COST_EXPLORER ");

        // Prepare filters
        List<Map<String, Object>> filters = costExplorerDto.getFilters();

        Map<String, List<String>> filterMap = new HashMap<>();
        for (int i = 0; i < filters.size(); i++) {
            Map<String, Object> filter = filters.get(i);
            String columnName = (String) filter.get("column");
            String value = (String) filter.get("value");
            String column = CostExplorerColumns.fromDisplayName(columnName).getDbColumnName();
            List<String> val;
            if (filterMap.containsKey(column)) {
                val = filterMap.get(column);
            } else {
                val = new ArrayList<>();
            }
            val.add(value);
            filterMap.put(column, val);
        }

        if (!filters.isEmpty()) {

            query.append("WHERE ");

            List<String> filterClauses = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
                String columnKey = entry.getKey();
                List<String> values = entry.getValue();

                if (values.size() == 1) {
                    filterClauses.add(columnKey + " = '" + values.get(0) + "'");
                } else {
                    StringBuilder orClause = new StringBuilder("(");
                    for (int i = 0; i < values.size(); i++) {
                        orClause.append(columnKey).append(" = '").append(values.get(i)).append("'");
                        if (i < values.size() - 1) {
                            orClause.append(" OR ");
                        }
                    }
                    orClause.append(")");
                    filterClauses.add(orClause.toString());
                }
            }

            log.info("filter OR clause",filterClauses.toString());
            for (int j = 0; j < filterClauses.size(); j++) {
                query.append(filterClauses.get(j));
                if (j < filterClauses.size() - 1) {
                    query.append(" AND ");
                }
            }
            query.append(" AND ");
        } else {
            query.append("WHERE ");
        }

        query.append("LINKEDACCOUNTID = ").append(costExplorerDto.getAccountId()).append(" ")
                .append("AND ").append(groupByDbColumn).append(" IS NOT NULL ");

        query.append("GROUP BY ")
                .append(groupByDbColumn)
                .append(", TO_CHAR(USAGESTARTDATE, 'MM-YYYY')");

        // Log and return
        log.info("SQL QUERY : {}", query);
        return query.toString();
    }
}
