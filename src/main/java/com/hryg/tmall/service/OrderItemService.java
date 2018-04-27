package com.hryg.tmall.service;

import com.hryg.tmall.pojo.Order;
import com.hryg.tmall.pojo.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderItemService {
    void add(OrderItem orderItem);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    List list();

    void fill(List<Order> orders);

    void fill(Order order);

    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);
}
