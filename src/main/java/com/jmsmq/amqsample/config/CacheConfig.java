package com.jmsmq.amqsample.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.jmsmq.amqsample.model.CacheNames.ADDRESS;
import static com.jmsmq.amqsample.model.CacheNames.EMPLOYEE;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(1000)
                .recordStats();  // Enables hit/miss monitoring
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        return new CaffeineCacheManager(EMPLOYEE, ADDRESS) {{
            setCaffeine(caffeine);
        }};
    }
}