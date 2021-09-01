package com.ethen.frame.common;

/**
 * 通信框架常量
 */
public interface NettyConst {
    /**
     * 默认host
     */
    String DEF_HOST = "127.0.0.1";

    /**
     * 默认端口
     */
    int DEF_PORT = 30000;

    /**
     * {@link io.netty.channel.ChannelOption.SO_BACKLOG}
     * 对应的是 tcp/ip 协议 listen 函数中的 backlog 参数，函数
     * listen(int socketfd,int backlog)用来初始化服务端可连接队列，
     */
    int LISTEN_QUEUE_CAP = 1000;
}
