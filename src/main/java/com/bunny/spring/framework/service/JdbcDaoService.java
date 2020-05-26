package com.bunny.spring.framework.service;

import com.bunny.spring.framework.dao.products.jdbc.ProductsDao;
import com.bunny.spring.framework.entity.Product;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Spring Cache采用“懒加载”策略
 * 未实现的场景：
 *   由单次查询触发，取出（关联）表中所有数据项（生成项），载入缓存
 *
 */
@CacheConfig(cacheNames = "products")
@Service
public class JdbcDaoService {

    @Resource(name = "jdbcProductDao")
    private ProductsDao jdbcProductDao;

    @Cacheable
    public Product queryById(Long id) throws Exception {
        return jdbcProductDao.queryById(id);
    }

    @CachePut
    public void update(Product product) throws Exception {
        jdbcProductDao.update(product);
    }

    @CacheEvict(allEntries = true)
    public Boolean evict() {
        return true;
    }
}
