package com.ethen.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        final EchoServer echoServer = new EchoServer(9999);
        System.out.println("EchoServer服务器即将启动...");
        echoServer.start();
        System.out.println("EchoServer服务器已关闭!!!");
    }

    // netty hello 服务启动逻辑
    private void start() {
        // TODO ...
    }
}
