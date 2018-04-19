package com.hryg.tmall.service.impl;

import com.hryg.tmall.mapper.CategoryMapper;
import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return null;
    }
}
