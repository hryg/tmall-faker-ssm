package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Review;

import java.util.List;

/**
 * @author hengrui
 */
public interface ReviewService {
    void add(Review review);

    void delete(int id);

    void update(Review review);

    Review get(int id);

    List list(int pid);

    int getCount(int pid);
}
