package com.bunny.spring.framework.dao.products.jdbc;

import com.bunny.spring.framework.entity.Product;

public interface ProductsDao {
    public Product queryById(Long id) throws Exception;
    public void update(Product product) throws Exception;
}
