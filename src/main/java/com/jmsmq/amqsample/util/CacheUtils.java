package com.jmsmq.amqsample.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {

    @Autowired
    private CacheManager cacheManager;

    public void evict(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            System.out.printf("Evicted key [%s] from cache [%s]%n", key, cacheName);
        }
    }

    public void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            System.out.printf("Cleared all entries from cache [%s]%n", cacheName);
        }
    }
}
