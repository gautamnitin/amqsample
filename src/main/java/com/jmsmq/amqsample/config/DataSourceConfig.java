package com.jmsmq.amqsample.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Configuration class for database connection pooling with HikariCP.
 * This class explicitly configures HikariCP to ensure optimal database connection management.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Creates the primary DataSource properties bean.
     * 
     * @return DataSourceProperties configured from application properties
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates the primary DataSource bean using HikariCP.
     * HikariCP is already the default connection pool for Spring Boot,
     * but this configuration makes it explicit and allows for additional customization.
     * 
     * @param properties The data source properties
     * @return A configured HikariDataSource
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
}