package com.bunny.spring.framework.dao.products.jdbc;

import com.bunny.spring.framework.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Repository注解与@Resource注解配对使用
 * @Repository功能如下
 *      1. 按名称value注册资源
 *      2. 执行异常翻译（Exception Translation）
 * @Resource按照名称name注入DAO资源
 *
 * 支持Spring 5全注解配置
 */
@Repository(value = "jdbcProductDao")
public class ProductsDaoI implements ProductsDao {

    @Autowired
    private JdbcTemplate template;

    public Product queryById(Long id) throws Exception {
        // string builder 支持链式调用.
        StringBuilder sb = new StringBuilder("SELECT * FROM products WHERE id=")
                .append(id);
        try {
            return template.query(sb.toString(), new RowMapper<Product>(){

                @Override
                public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                    Long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Long price = resultSet.getLong(4);
                    return new Product(id, name, description, price);
                }
            }).get(0);
        } catch (Exception e) {
            throw new Exception();
        }
    }


    public void update(Product product) throws Exception {
        StringBuilder sb = new StringBuilder("INSERT INTO `products`(name, description, price) VALUES(\"")
                .append(product.getName())
                .append("\", \"")
                .append(product.getDescription())
                .append("\", ")
                .append(product.getPrice())
                .append(")");
        try {
            template.execute(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
