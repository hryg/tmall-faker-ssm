package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    List<Product> list(int cid);
}