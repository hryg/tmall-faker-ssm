package com.hryg.tmall.service.impl;

import com.hryg.tmall.mapper.OrderMapper;
import com.hryg.tmall.pojo.Order;
import com.hryg.tmall.pojo.OrderExample;
import com.hryg.tmall.pojo.OrderItem;
import com.hryg.tmall.pojo.User;
import com.hryg.tmall.service.OrderItemService;
import com.hryg.tmall.service.OrderService;
import com.hryg.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;

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

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        add(order);
        for (OrderItem oi: orderItems) {
            oi.setOid(order.getId());
            orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
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
