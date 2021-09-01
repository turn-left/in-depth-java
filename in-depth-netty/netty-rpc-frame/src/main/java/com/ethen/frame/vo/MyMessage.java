package com.ethen.frame.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 自定义协议框架消息实体
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MyMessage {
    private MyHeader header;
    private Object body;
}
