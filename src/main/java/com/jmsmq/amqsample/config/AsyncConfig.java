package com.jmsmq.amqsample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "tradeExecutor")
    public Executor tradeExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Increase thread pool size for better throughput
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        // Set rejection policy to caller runs to avoid losing messages
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // Set keep-alive time to reuse threads efficiently
        executor.setKeepAliveSeconds(120);
        executor.setThreadNamePrefix("TradeProcessor-");
        executor.initialize();
        return executor;
    }
    
    @Bean(name = "financialDataExecutor")
    public Executor financialDataExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Increase thread pool size for better throughput with 10M records
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        // Set rejection policy to caller runs to avoid losing messages
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // Set keep-alive time to reuse threads efficiently
        executor.setKeepAliveSeconds(120);
        executor.setThreadNamePrefix("FinancialDataProcessor-");
        executor.initialize();
        return executor;
    }
}
