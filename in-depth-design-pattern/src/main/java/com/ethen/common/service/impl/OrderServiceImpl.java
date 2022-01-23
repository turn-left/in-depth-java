package com.ethen.common.service.impl;

import com.ethen.common.model.Order;
import com.ethen.common.service.OrderService;

import java.util.Collections;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public List<Order> selectOrderById(String orderId) {
        System.err.println("execute SQL: select * from Order where orderId=" + orderId);

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCost(1001.06d);

        return Collections.singletonList(order);
    }
}
