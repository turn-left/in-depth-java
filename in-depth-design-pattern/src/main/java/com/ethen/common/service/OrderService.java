package com.ethen.common.service;

import com.ethen.common.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> selectOrderById(String orderId);
}
