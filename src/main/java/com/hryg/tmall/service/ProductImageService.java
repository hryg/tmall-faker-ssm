package com.hryg.tmall.service;

import com.hryg.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {
    String TYPE_SINGLE = "type_single";
    String TYPE_DETAIL = "type_detail";

    void add(ProductImage productImage);

    void delete(int id);

    void update(ProductImage productImage);

    ProductImage get(int id);

    List<ProductImage> list(int pid, String type);
}
