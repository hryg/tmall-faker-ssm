package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Product;
import com.hryg.tmall.pojo.Property;
import com.hryg.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    void init(Product product);

    void update(PropertyValue propertyValue);

    PropertyValue get(int ptid, int pid);

    List<PropertyValue> list(int pid);
}
