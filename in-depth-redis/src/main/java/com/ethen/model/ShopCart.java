package com.ethen.model;

import lombok.Data;

import java.util.Map;

/**
 * 购物车
 */
@Data
public class ShopCart {
    private String cartId;
    private String userId;
    private Map<String, String> goodsAmounts;
}
