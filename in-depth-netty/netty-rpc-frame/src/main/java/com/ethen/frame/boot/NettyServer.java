package com.ethen.frame.boot;

import com.ethen.frame.common.NettyConst;
import com.ethen.frame.server.ServerInit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void bind() throws InterruptedException {
        ServerBootstrap boot = new ServerBootstrap();
        final EventLoopGroup group = new NioEventLoopGroup();
        boot.group(group)
                .channel(NioServerSocketChannel.class)
                // fixme TCP/IP参数底层含义？？？
                .option(ChannelOption.SO_BACKLOG, NettyConst.LISTEN_QUEUE_CAP)
                .childHandler(new ServerInit());
        boot.bind(port).sync();
        log.info("NettyServer start: host => " + NettyConst.DEF_HOST + ",port => " + port);
    }
}
