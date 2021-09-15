package com.ethen.server;

import com.ethen.handler.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

public class ChatServer {
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap boot = new ServerBootstrap();
        boot.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChatServerInitializer(channelGroup));
        final ChannelFuture future = boot.bind(address);
        future.syncUninterruptibly();
        this.channel = future.channel();
        return future;
    }

    public void destroy() {
        if (channel != null) channel.close();
        channelGroup.close();
        eventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please give port as argument!");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        final ChatServer server = new ChatServer();
        final ChannelFuture future = server.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread(server::destroy));
        future.channel().closeFuture().syncUninterruptibly();
    }

}
