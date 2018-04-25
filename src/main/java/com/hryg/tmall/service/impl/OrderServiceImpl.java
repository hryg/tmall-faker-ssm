package com.hryg.tmall.service.impl;

import com.hryg.tmall.mapper.OrderMapper;
import com.hryg.tmall.pojo.Order;
import com.hryg.tmall.pojo.OrderExample;
import com.hryg.tmall.pojo.User;
import com.hryg.tmall.service.OrderItemService;
import com.hryg.tmall.service.OrderService;
import com.hryg.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        setUser(orders);
        return orders;
    }

    public void setUser(Order order) {
        int uid = order.getUid();
        User user = userService.get(uid);
        order.setUser(user);
    }

    public void setUser(List<Order> orders) {
        for (Order order : orders) {
            setUser(order);
        }
    }
}
