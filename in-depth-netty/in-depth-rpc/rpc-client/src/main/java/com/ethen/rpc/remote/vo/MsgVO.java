package com.ethen.rpc.remote.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class MsgVO implements Serializable {
    // 消息接收地址
    private String target;
    // 消息标题
    private String title;
    // 消息内容
    private String content;
    // 消息发送者
    private String sourceAddress;
}
