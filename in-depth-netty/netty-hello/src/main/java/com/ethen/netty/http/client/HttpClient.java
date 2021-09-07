package com.ethen.netty.http.client;

import com.ethen.netty.common.NettyConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class HttpClient {
    public static final String HOST = "127.0.0.1";
    private final String remoteHost;
    private final int port;


    public HttpClient(String remoteHost, int port) {
        this.port = port;
        this.remoteHost = remoteHost;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientHandlerInit())
                .remoteAddress(new InetSocketAddress(remoteHost, port));
        try {
            ChannelFuture future = boot.connect().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HttpClient(NettyConst.DEF_HOST, NettyConst.NETTY_HTTP_PORT).run();
    }
}
