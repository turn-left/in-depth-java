package com.ethen.netty.udp.unicast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * UDP单播消息发应答方
 */
public class UniAnswerSide {
    private final int port;

    static final String ANSWER = "古诗来了！";

    public UniAnswerSide(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new UniAnswerHandler());
        try {
            // 没有接收客户端连接的过程，监听本地端口即可
            ChannelFuture future = boot.bind(this.port).sync();
            System.out.println("UniAnswerSide应答服务已经启动...");
            future.channel().closeFuture().sync(); // fixme ???
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        UniAnswerSide answerSide = new UniAnswerSide(10086);
        answerSide.run();
    }

}
