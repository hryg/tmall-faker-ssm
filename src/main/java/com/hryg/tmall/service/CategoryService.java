package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.util.Page;

import java.util.List;

public interface CategoryService {
    List<Category> list(Page page);
    int total();
}
