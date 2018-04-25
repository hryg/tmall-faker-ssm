package com.hryg.tmall.service.impl;

import com.hryg.tmall.mapper.ProductMapper;
import com.hryg.tmall.pojo.Category;
import com.hryg.tmall.pojo.Product;
import com.hryg.tmall.pojo.ProductExample;
import com.hryg.tmall.pojo.ProductImage;
import com.hryg.tmall.service.CategoryService;
import com.hryg.tmall.service.ProductImageService;
import com.hryg.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductMapper productMapper;

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setCategory(product);
        setFirstProductImage(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(example);
        setCategory(products);
        setFirstProductImage(products);
        return products;
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> productImages = productImageService.list(product.getId(), ProductImageService.TYPE_SINGLE);
        if (!productImages.isEmpty()) {
            product.setFirstProductImage(productImages.get(0));
        }
    }

    public void setFirstProductImage(List<Product> products) {
        for (Product product :products) {
            setFirstProductImage(product);
        }
    }

    public void setCategory(Product product) {
        int cid = product.getCid();
        Category category = categoryService.get(cid);
        product.setCategory(category);
    }

    public void setCategory(List<Product> products) {
        for (Product product : products) {
            setCategory(product);
        }
    }
}
