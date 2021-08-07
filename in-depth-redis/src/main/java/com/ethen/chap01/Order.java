package com.ethen.chap01;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Order {
    private Long orderId;
    private Double money;
    private Date time;
}
