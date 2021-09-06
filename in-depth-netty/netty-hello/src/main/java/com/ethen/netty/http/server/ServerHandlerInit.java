package com.ethen.netty.http.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;

/**
 * TCP粘包半包处理
 * HTTP消息解码
 * HTTP消息编码
 * 消息报文压缩
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {
    private static final int MAX_CONTENT_LENGTH = 10 * 1024 * 1024;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();
        /* 处理应答消息的编码 */
        pl.addLast("encoder", new HttpRequestEncoder());
        /* 处理请求消息的解码 fixme 粘包、拆包解决？？？ */
        pl.addLast("decoder", new HttpRequestDecoder());
        /* 聚合HTTP消息为一个完整报文 */
        pl.addLast("aggregator", new HttpObjectAggregator(MAX_CONTENT_LENGTH));
        /* 应答报文压缩 非必要 */
        pl.addLast("compressor", new HttpContentCompressor());
        pl.addLast(new BusinessHandler());
    }
}
