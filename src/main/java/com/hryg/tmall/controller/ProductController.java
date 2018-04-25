package com.hryg.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.pojo.Product;
import com.hryg.tmall.service.CategoryService;
import com.hryg.tmall.service.ProductService;
import com.hryg.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author hengrui
 */
@Controller
//@RequestMapping("")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_add")
    public String add(Model model, Product product) {
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(int id, Model model) {
        Product product = productService.get(id);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("product", product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product) {
        Date date = productService.get(product.getId()).getCreateDate();
        product.setCreateDate(date);
        productService.update(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> products = productService.list(cid);
        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);

        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("page", page);

        return "admin/listProduct";
    }
}
