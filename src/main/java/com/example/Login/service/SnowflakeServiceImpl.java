package com.example.Login.service;

import com.example.Login.dto.Snowflake.*;
import com.example.Login.exceptionhandler.SnowflakeServiceException;
import com.example.Login.service.serviceInterfaces.SnowflakeService;
import com.example.Login.snowflake.repository.SnowflakeRepository;
import com.example.Login.utils.SqlQueryGenerator;
import lombok.RequiredArgsConstructor;
import net.snowflake.client.jdbc.SnowflakeSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnowflakeServiceImpl implements SnowflakeService{

    @Autowired
    SqlQueryGenerator sqlQueryGenerator;

    @Autowired
    SnowflakeRepository snowflakeRepository;

    public QueryResponse getSumByGroupAndFilter(CostExplorerDto explorerDto){
        try{

            QueryResponse queryResponse = snowflakeRepository.getSumByGroupAndFilter(
                    SqlQueryGenerator.queryGenerator(explorerDto)
            );

            List<Map<String, Object>> resultList = queryResponse.getResult();
            List<Map<String, Object>> top5EntriesAndOthers = new ArrayList<>();

            int topN = Math.min(5, resultList.size());
            List<Map<String, Object>> top5 = resultList.subList(0, topN);
            top5EntriesAndOthers.addAll(top5);


            double otherTotal = resultList.subList(topN, resultList.size()).stream()
                    .map(e -> e.get("TOTAL_AMOUNT"))
                    .mapToDouble(val -> ((double) val))
                    .sum();

            if (resultList.size() > 5) {
                Map<String, Object> otherEntry = new HashMap<>();
                otherEntry.put("label", "Other");
                otherEntry.put("value", otherTotal);
                String usageMonth = (String) resultList.get(topN).get("USAGE_MONTH");
                otherEntry.put("USAGE_MONTH", usageMonth);
                top5EntriesAndOthers.add(otherEntry);
            }

            queryResponse.setResult(top5EntriesAndOthers);
            return queryResponse;
        } catch(DataAccessException e){
            throw new SnowflakeServiceException("Error Accessing Snowflake, Data Access Exception");
        } catch (Exception e) {
            throw new RuntimeException("Error occured in snowflake service");
        }
    }


    public List<String> getAllColumns() {
        try{

            return Arrays.stream(CostExplorerColumns.values())
                    .filter(CostExplorerColumns::getDisplay)
                    .map(CostExplorerColumns::getDisplayName)
                    .toList();
        }catch (DataAccessException e){
            throw new SnowflakeServiceException("Issue getting Column Data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllDistinctValues(String columnName) {
        try{

            String columnDBName = CostExplorerColumns.fromDisplayName(columnName).getDbColumnName();
            return snowflakeRepository.getAllDistinctValues(columnDBName);
        }catch (DataAccessException e){
            throw new SnowflakeServiceException("Issue getting Column Data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
