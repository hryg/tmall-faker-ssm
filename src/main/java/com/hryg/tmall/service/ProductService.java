package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    List<Product> list(int cid);

    void setFirstProductImage(Product product);

    void fill(List<Category> categories);

    void fill(Category category);

    void fillByRow(List<Category> categories);

    void setSaleAndReviewNumber(Product product);

    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword);
}
