package com.ethen.common.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * 订单
 */
@Data
public class Order {
    private String orderId = UUID.randomUUID().toString();
    private double cost;
    private String userId;
    private List<String> goods;
    private List<Integer> goodsCount;
}
