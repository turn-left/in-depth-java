package com.ethen.model;

import lombok.Data;

@Data
public class GoodsReq {
    private String goodsId;
    private Long amount;
    private String userId;
}
