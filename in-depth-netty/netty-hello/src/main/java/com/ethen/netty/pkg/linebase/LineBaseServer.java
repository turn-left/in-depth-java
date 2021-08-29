package com.ethen.netty.pkg.linebase;

import com.ethen.netty.common.NettyConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.net.InetSocketAddress;

public class LineBaseServer {
    private final int port;

    public LineBaseServer(int port) {
        this.port = port;
    }

    public void startServer() {
        // 事件循环组
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        // 启动必备引导
        ServerBootstrap boot = new ServerBootstrap();
        ChannelHandler serverHandler = new LineBaseServerHandler();
        boot.group(boss, worker)
                /*指定NioSocketChannel来处理连接*/
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(NettyConst.LINE_BASE_MAX_LEN));
                        ch.pipeline().addLast(serverHandler);
                    }
                });
        try {
            final ChannelFuture future = boot.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        final LineBaseServer server = new LineBaseServer(NettyConst.DEF_PKG_PORT);
        System.err.println("PkgServer服务器即将启动...");
        server.startServer();
        System.err.println("PkgServer服务器停止服务...");
    }
}
