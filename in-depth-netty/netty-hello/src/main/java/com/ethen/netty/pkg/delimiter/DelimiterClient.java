package com.ethen.netty.pkg.delimiter;

import com.ethen.netty.common.NettyConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class DelimiterClient {
    private final String host;
    private final int port;

    public DelimiterClient(String host, int port) {
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
                        ByteBuf delimiter = Unpooled.copiedBuffer(NettyConst.DEF_DELIMITER.getBytes(StandardCharsets.UTF_8));
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(NettyConst.LINE_BASE_MAX_LEN, delimiter));
                        ch.pipeline().addLast(new DelimiterClientHandler());
                    }
                });
        try {
            ChannelFuture future = boot.connect().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        DelimiterClient client = new DelimiterClient(NettyConst.DEF_HOST, NettyConst.DEF_PKG_PORT);
        client.doConnect();
    }
}
