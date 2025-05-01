package com.example.Login.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Slf4j
public class SnowflakeConfig {


    private final String url = System.getenv("SNOWFLAKE_URL");
    private final String username = System.getenv("SNOWFLAKE_USER");
    private final String password = System.getenv("SNOWFLAKE_PASSWORD");
    private final String warehouse = System.getenv("SNOWFLAKE_WAREHOUSE");

    @Bean(name = "snowflakeDataSource")
    public DataSource snowflakeDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        String connectionUrl = url;


        log.info("Snowflake JDBC URL: {}", connectionUrl);
        log.info("Initializing Snowflake warehouse: {}", warehouse);

        dataSource.setJdbcUrl(connectionUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("net.snowflake.client.jdbc.SnowflakeDriver");

        return dataSource;
    }

    @Bean(name = "snowflakeJdbcTemplate")
    public JdbcTemplate snowflakeJdbcTemplate(@Qualifier("snowflakeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
