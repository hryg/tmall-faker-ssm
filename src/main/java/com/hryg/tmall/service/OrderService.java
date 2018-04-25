package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Order;

import java.util.List;

public interface OrderService {
    String WAIT_PAY = "waitPay";
    String WAIT_DELIVERY = "waitDelivery";
    String WAIT_CONFIRM = "waitConfirm";
    String WAIT_REVIEW = "waitReview";
    String FINISH = "finish";
    String DELETE = "delete";

    void add(Order order);

    void delete(int id);

    void update(Order order);

    Order get(int id);

    List<Order> list();
}
