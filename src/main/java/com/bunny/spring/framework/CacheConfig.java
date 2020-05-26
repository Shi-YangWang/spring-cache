package com.bunny.spring.framework;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.*;

@Configuration
public class CacheConfig {

//    Jdk Implementation
//    @Bean
//    public SimpleCacheManager getCacheManager() {
//        List<ConcurrentMapCache> caches = new LinkedList<>();
//        ConcurrentMapCacheFactoryBean factoryBean = new ConcurrentMapCacheFactoryBean();
//        factoryBean.setName("products");
//        factoryBean.afterPropertiesSet();
//        caches.add(factoryBean.getObject());
//
//        SimpleCacheManager manager = new SimpleCacheManager();
//        manager.setCaches(caches);
//        return manager;
//    }

//    Ehcache Implementation
//    @Bean
//    public CacheManager getCacheManager() {
//        ResourceLoader loader = new DefaultResourceLoader();
//        Resource ehcacheConfig = loader.getResource("ehcache/ehcache.xml");
//        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
//        factoryBean.setConfigLocation(ehcacheConfig);
//        factoryBean.afterPropertiesSet();
//        return factoryBean.getObject();
//    }
//
//    @Bean
//    public EhCacheCacheManager getCacheCacheManager() {
//        EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager();
//        cacheCacheManager.setCacheManager(getCacheManager());
//        cacheCacheManager.afterPropertiesSet();
//        return cacheCacheManager;
//    }

//    Redis Implementation
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存管理器管理的缓存的默认过期时间
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(240))
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("products");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("products", defaultCacheConfig.entryTtl(Duration.ofSeconds(180)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }

}
