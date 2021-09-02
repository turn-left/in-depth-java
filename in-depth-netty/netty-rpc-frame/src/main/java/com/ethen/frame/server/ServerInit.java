package com.ethen.frame.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 服务端ChannelHandler初始化
 * <p>
 * 解决半包/粘包问题(消息头/消息体)
 * 序列化/反序列化(byte->MyMessage)
 * 登录握手(身份验证，白名单鉴权)
 * 心跳检测
 * 为方便二进制协议调试引入netty自带LoggingHandler(LogLevel.INFO)
 */
public class ServerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /*
         * 半包/粘包问题
         * LengthFieldBasedFrameDecoder
         * LengthFieldPrepender
         */
    }
}
