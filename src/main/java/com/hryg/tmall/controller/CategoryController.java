package com.hryg.tmall.controller;

import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.service.CategoryService;
import com.hryg.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hryg
 */
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        List<Category> categories = categoryService.list(page);
        int total = categoryService.total();
        page.setTotal(total);
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }
}
