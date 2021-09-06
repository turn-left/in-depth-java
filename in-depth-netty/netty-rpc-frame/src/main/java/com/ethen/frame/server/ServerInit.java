package com.ethen.frame.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 服务端ChannelHandler初始化
 * <p>
 * 1.解决半包/粘包问题(消息头/消息体)
 * 2.序列化/反序列化(byte->MyMessage)
 * 3.登录握手(身份验证，白名单鉴权)
 * 4.心跳检测
 * 5.为方便二进制协议调试引入netty自带LoggingHandler(LogLevel.INFO)
 * <p>
 * 关于自定长度解码器解决粘包半包问题
 * 发送数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment
 * {@link => https://blog.csdn.net/liyantianmin/article/details/85603347}
 */
public class ServerInit extends ChannelInitializer<SocketChannel> {
    static final int MAX_FRAME_LEN = 65535;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /*
         * 半包/粘包问题
         * LengthFieldBasedFrameDecoder
         * LengthFieldPrepender
         * @see com.ethen.frame.vo.MyMessage
         */
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                MAX_FRAME_LEN, 0, 2, 0, 0));
        // fixme what???
        ch.pipeline().addLast(new LengthFieldPrepender(2));





    }
}
