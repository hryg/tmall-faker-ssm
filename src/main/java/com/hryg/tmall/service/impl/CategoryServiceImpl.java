package com.hryg.tmall.service.impl;

import com.hryg.tmall.mapper.CategoryMapper;
import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return null;
    }
}
