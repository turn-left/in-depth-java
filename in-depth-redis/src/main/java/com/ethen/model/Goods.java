package com.ethen.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品
 */
@Getter
@Setter
@ToString
public class Goods {
    private String id;
    private String name;
    private String price;
    private String imgUrl;
}
