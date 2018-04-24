package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.util.Page;

import java.util.List;

public interface CategoryService {
    List<Category> list(Page page);

    int total();

    void add(Category category);

    void delete(int id);

    Category get(int id);

    void update(Category category);
}
