package com.bunny.spring.framework;

import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public SimpleCacheManager getCacheManager() {
        List<ConcurrentMapCache> caches = new LinkedList<>();
        ConcurrentMapCacheFactoryBean factoryBean = new ConcurrentMapCacheFactoryBean();
        factoryBean.setName("products");
        factoryBean.afterPropertiesSet();
        caches.add(factoryBean.getObject());

        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(caches);
        return manager;
    }
}
