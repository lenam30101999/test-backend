package com.aibles.categoryservice.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        Cache category = new ConcurrentMapCache("category");
        Cache listCategory = new ConcurrentMapCache("listCategory");

        cacheManager.setCaches(Arrays.asList(category, listCategory));
        return cacheManager;
    }
}
