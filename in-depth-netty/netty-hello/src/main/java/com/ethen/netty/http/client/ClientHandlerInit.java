package com.ethen.netty.http.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 消息编码
 * 服务器响应消息的解码
 * 服务器响应消息的解压
 * 客户端发送消息
 */
public class ClientHandlerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
    }
}
