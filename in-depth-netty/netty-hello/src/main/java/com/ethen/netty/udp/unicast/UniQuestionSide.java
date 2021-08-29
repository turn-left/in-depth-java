package com.ethen.netty.udp.unicast;

import com.ethen.netty.common.NettyConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * UDP单播消息发送方
 */
public class UniQuestionSide {
    static final String QUESTION = "告诉我一句古诗~";
    static final long TIMEOUT = 15000;

    public void run(int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.group(group)
                // 使用UDP通信
                .channel(NioDatagramChannel.class)
                .handler(new UniQuestionHandler());
        try {
            // A port number of zero will let the system pick up an ephemeral port in a bind operation.
            Channel channel = boot.bind(NettyConst.EPHEMERAL_PORT).sync().channel();
            channel.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer(QUESTION.getBytes(StandardCharsets.UTF_8)),
                            new InetSocketAddress(NettyConst.DEF_HOST, port)))
                    .sync();
            // fixme 2021/8/29 why do this ???
            if (!channel.closeFuture().await(TIMEOUT)) {
                System.err.println("UniQuestionSide等待超时！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        UniQuestionSide questionSide = new UniQuestionSide();
        questionSide.run(10086);
    }
}
