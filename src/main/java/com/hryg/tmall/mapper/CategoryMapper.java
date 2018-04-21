package com.hryg.tmall.mapper;

import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.util.Page;

import java.util.List;

/**
 * @author hryg
 */
public interface CategoryMapper {
    List<Category> list(Page page);
    int total();
}
