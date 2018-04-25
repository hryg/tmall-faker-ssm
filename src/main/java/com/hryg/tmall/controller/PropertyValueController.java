package com.hryg.tmall.controller;

import com.hryg.tmall.pojo.Product;
import com.hryg.tmall.pojo.Property;
import com.hryg.tmall.pojo.PropertyValue;
import com.hryg.tmall.service.ProductService;
import com.hryg.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid) {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.list(product.getId());
        model.addAttribute("product", product);
        model.addAttribute("propertyValues", propertyValues);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return "success";
    }
}
