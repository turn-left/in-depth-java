package com.ethen.netty.pkg.linebase;

import com.ethen.netty.common.NettyConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.net.InetSocketAddress;

public class LineBaseClient {
    private final String host;
    private final int port;

    public LineBaseClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void doConnect() {
        Bootstrap boot = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        boot.channel(NioSocketChannel.class)
                .group(group)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(NettyConst.LINE_BASE_MAX_LEN));
                        ch.pipeline().addLast(new LineBaseClientHandler());
                    }
                });
        try {
            ChannelFuture future = boot.connect().sync();
            future.channel().closeFuture().sync(); // fixme what is this mean??
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        LineBaseClient client = new LineBaseClient(NettyConst.DEF_HOST, NettyConst.DEF_PKG_PORT);
        client.doConnect();
    }
}
