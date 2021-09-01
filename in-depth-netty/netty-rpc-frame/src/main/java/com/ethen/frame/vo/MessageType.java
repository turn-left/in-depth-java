package com.ethen.frame.vo;

/**
 * 通信消息类型
 */
public enum MessageType {
    BUSINESS_REQ, // 业务请求消息
    BUSINESS_RESP, // 业务应答消息
    ONEWAY, // 无需应答的业务请求消息
    LOGIN_REQ, // 登录请求消息
    LOGIN_RESP, // 登录应答消息
    HEARTBEAT_REQ, // 心跳请求消息
    HEARTBEAT_RESP; // 心跳应答消息

    /**
     * 获取消息类型的值
     */
    public byte value() {
        return (byte) ordinal();
    }
}
