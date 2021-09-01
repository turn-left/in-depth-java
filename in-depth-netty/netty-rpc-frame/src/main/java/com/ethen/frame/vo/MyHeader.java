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
    // CRC校验 fixme ???
    private int crcCode = 0xabef0101;

    // 消息长度
    private int length;

    // 会话ID
    private long sessionId;

    // 消息类型
    private byte type;

    // 消息优先级
    private byte priority;

    // 消息头额外附件 fixme ???
    private Map<String, Object> attachment = new HashMap<>();
}
