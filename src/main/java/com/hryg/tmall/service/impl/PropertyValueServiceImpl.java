package com.hryg.tmall.service.impl;

import com.hryg.tmall.mapper.PropertyValueMapper;
import com.hryg.tmall.pojo.Product;
import com.hryg.tmall.pojo.Property;
import com.hryg.tmall.pojo.PropertyValue;
import com.hryg.tmall.pojo.PropertyValueExample;
import com.hryg.tmall.service.PropertyService;
import com.hryg.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.list(product.getCid());
        for (Property property : properties) {
            PropertyValue propertyValue = get(property.getId(), product.getId());
            if (null == property) {
                propertyValue = new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.insert(propertyValue);
            }
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andPtidEqualTo(ptid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        if (propertyValues.isEmpty()) {
            return null;
        }
        return propertyValues.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        for (PropertyValue propertyValue : propertyValues) {
            Property property = propertyService.get(propertyValue.getPtid());
            propertyValue.setProperty(property);
        }
        return propertyValues;
    }
}
