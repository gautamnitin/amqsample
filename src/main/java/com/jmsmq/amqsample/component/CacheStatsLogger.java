package com.jmsmq.amqsample.component;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CacheStatsLogger {

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 60000)
    public void logStats() {
        Arrays.asList("employeeCache", "addressCache").forEach(cacheName -> {
            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
            CacheStats stats = cache.getNativeCache().stats();
            System.out.printf("[%s] Hits: %d, Misses: %d, Evictions: %d%n",
                    cacheName, stats.hitCount(), stats.missCount(), stats.evictionCount());
        });
    }
}
