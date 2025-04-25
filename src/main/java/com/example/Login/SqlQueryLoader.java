package com.example.Login;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@Slf4j
@PropertySource("classpath:/sql.properties")
@Getter
public class SqlQueryLoader {

    private final Properties properties =  new Properties();

    @Value("${account.cost.count}")
    private String getAccountCostCount;

    @PostConstruct
    public void init(){
        log.info("SQL queries are loaded successfully");
    }
}
