package com.bunny.spring.framework.controller;

import com.bunny.spring.framework.entity.Product;
import com.bunny.spring.framework.service.HibernateDaoService;
import com.bunny.spring.framework.service.JdbcDaoService;
import com.bunny.spring.framework.service.MyBatisDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SKUController {

    @Autowired
    private JdbcDaoService service;

    @Autowired
    private MyBatisDaoService myBatisDaoService;

    @Autowired
    private HibernateDaoService hibernateDaoService;

    @GetMapping("/query/jdbc/{id}")
    public String queryByIdJdbc(@PathVariable(name = "id", required = true) Long id) {
        try {
            Product product = service.queryById(id);
            return product.getName();
        } catch (Exception e) {
            return "500 Not Found";
        }
    }


    @GetMapping("/query/jdbc/update")
    public String updateJdbc(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") Long price) {
        try {
            service.update(
                    new Product().
                            setName(name).
                            setDescription(description)
                            .setPrice(price)
            );
            return "200 OK";
        } catch (Exception e) {
            return "500 Not Found";
        }
    }


    @GetMapping("/query/jdbc/evict")
    public String evictJdbc() {
        try {
            service.evict();
            return "200 OK";
        } catch (Exception e) {
            return "500 Not Found";
        }
    }


    @GetMapping("/query/ibatis/{id}")
    public String queryByIdMybatis(@PathVariable(name = "id", required = true) Long id) {
        try {
            Product product = myBatisDaoService.queryById(id);
            return product.getName();
        } catch (Exception e) {
            return "500 Not Found";
        }
    }


    @GetMapping("/query/hibernate/{id}")
    public String queryByIdHibernate(@PathVariable(name = "id", required = true) Long id) {
        try {
            Product product = hibernateDaoService.queryById(id);
            return product.getName();
        } catch (Exception e) {
            return "500 Not Found";
        }
    }
}
