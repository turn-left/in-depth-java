package com.ethen.frame.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义协议消息头
 */
@Getter
@Setter
@ToString
public final class MyHeader {
    // CRC校验 fixme ??? 4byte
    private int crcCode = 0xabef0101;

    // 消息长度 2byte
    private int length;

    // 会话ID 8byte
    private long sessionId;

    // 消息类型 1byte
    private byte type;

    // 消息优先级 1byte
    private byte priority;

    // 消息头额外附件 fixme ???  可变长度
    private Map<String, Object> attachment = new HashMap<>();
}
