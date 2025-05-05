package com.example.Login.service;

import com.example.Login.dto.Snowflake.*;
import com.example.Login.entity.AwsAccounts;
import com.example.Login.entity.User;
import com.example.Login.enums.CostExplorerColumns;
import com.example.Login.exceptionhandler.AccountDoesNotExists;
import com.example.Login.exceptionhandler.AccountNotAssociated;
import com.example.Login.exceptionhandler.SnowflakeServiceException;
import com.example.Login.exceptionhandler.UserNotFoundException;
import com.example.Login.repository.UserRepository;
import com.example.Login.security.jwt.JWTService;
import com.example.Login.service.serviceInterfaces.SnowflakeService;
import com.example.Login.snowflakeRepository.SnowflakeRepository;
import com.example.Login.utils.SqlQueryGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnowflakeServiceImpl implements SnowflakeService {

    @Autowired
    private SqlQueryGenerator sqlQueryGenerator;

    @Autowired
    private SnowflakeRepository snowflakeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwt;

    public QueryResponse getSumByGroupAndFilter(CostExplorerDto explorerDto, String token) {
        try {

            String email = jwt.extractEmail(token.substring(7));
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new UserNotFoundException("User Does not exists"));


            if (user.getAwsAccountsList().stream()
                    .filter(accounts -> accounts.getAccountId().toString()
                            .equalsIgnoreCase(explorerDto.getAccountId()))
                    .toList().isEmpty() && user.getRole().getRole().name().equalsIgnoreCase("customer"))
            {
                throw new AccountNotAssociated("Account is not associated with current user");
            }

            QueryResponse queryResponse = snowflakeRepository.getSumByGroupAndFilter(
                    SqlQueryGenerator.queryGenerator(explorerDto,"1000")
            );

            List<Map<String, Object>> resultList = queryResponse.getResult();
            Map<String, List<Map<String, Object>>> groupedByMonth = resultList.stream()
                    .collect(Collectors.groupingBy(e -> (String) e.get("USAGE_MONTH")));

            List<Map<String, Object>> finalResult = new ArrayList<>();

            for (String month : groupedByMonth.keySet()) {
                List<Map<String, Object>> entries = groupedByMonth.get(month);
                entries.sort((a, b) -> Double.compare(
                        ((Number) b.get("TOTAL_AMOUNT")).doubleValue(),
                        ((Number) a.get("TOTAL_AMOUNT")).doubleValue()
                ));

                int topN = Math.min(5, entries.size());
                List<Map<String, Object>> top5 = entries.subList(0, topN);
                finalResult.addAll(top5);

                if (entries.size() > 5) {
                    double othersSum = entries.subList(topN, entries.size()).stream()
                            .mapToDouble(e -> ((Number) e.get("TOTAL_AMOUNT")).doubleValue())
                            .sum();

                    Map<String, Object> otherEntry = new HashMap<>();
                    otherEntry.put("label", "Other");
                    otherEntry.put("value", othersSum);
                    otherEntry.put("USAGE_MONTH", month);
                    finalResult.add(otherEntry);
                }
            }

            queryResponse.setResult(finalResult);

            return queryResponse;

        } catch (DataAccessException e) {
            throw new SnowflakeServiceException("Error Accessing Snowflake, Data Access Exception");
        }
    }


    public List<String> getAllColumns() {
        try {

            return Arrays.stream(CostExplorerColumns.values())
                    .filter(CostExplorerColumns::getDisplay)
                    .map(CostExplorerColumns::getDisplayName)
                    .toList();
        } catch (DataAccessException e) {
            throw new SnowflakeServiceException("Issue getting Column Data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllDistinctValues(String columnName) {
        try {

            String columnDBName = CostExplorerColumns.fromDisplayName(columnName).getDbColumnName();
            return snowflakeRepository.getAllDistinctValues(columnDBName);
        } catch (DataAccessException e) {
            throw new SnowflakeServiceException("Issue getting Column Data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
