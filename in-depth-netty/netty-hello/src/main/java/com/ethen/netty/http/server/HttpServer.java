package com.ethen.netty.http.server;

import com.ethen.netty.common.NettyConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty实现简易HTTP服务器
 */
public class HttpServer {
    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap boot = new ServerBootstrap();
        boot.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerHandlerInit())
                .localAddress(this.port);
        try {
            ChannelFuture future = boot.bind().sync();
            System.err.println("HttpServer服务器启动成功，在端口：" + this.port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HttpServer(NettyConst.NETTY_HTTP_PORT).start();
    }
}
