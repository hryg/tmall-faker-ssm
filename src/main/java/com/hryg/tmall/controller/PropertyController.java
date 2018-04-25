package com.hryg.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.pojo.Property;
import com.hryg.tmall.service.CategoryService;
import com.hryg.tmall.service.PropertyService;
import com.hryg.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hengrui
 */
@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_property_add")
    public String add(Model model, Property property) {
        propertyService.add(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id) {
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());
        property.setCategory(category);
        model.addAttribute("property", property);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Property property) {
        propertyService.update(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> properties = propertyService.list(cid);
        int total = (int) new PageInfo<>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);

        model.addAttribute("properties", properties);
        model.addAttribute("category", category);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }
}
