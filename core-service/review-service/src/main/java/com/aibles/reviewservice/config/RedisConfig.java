package com.aibles.reviewservice.config;

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
        Cache review = new ConcurrentMapCache("review");
        Cache listReview = new ConcurrentMapCache("listReview");
        Cache listReviewUser = new ConcurrentMapCache("listReviewUser");
        Cache listReviewProduct = new ConcurrentMapCache("listReviewProduct");

        cacheManager.setCaches(Arrays.asList(review, listReview, listReviewUser, listReviewProduct));
        return cacheManager;
    }
}
