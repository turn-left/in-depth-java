package com.ethen.netty.http.client;

import com.ethen.netty.common.NettyConst;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * 消息编码
 * 服务器响应消息的解码
 * 服务器响应消息的解压
 * 客户端发送消息
 */
public class ClientHandlerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();
//        pl.addLast(new HttpRequestEncoder());
//        pl.addLast(new HttpResponseDecoder());
        /*消息编解码*/
        pl.addLast(new HttpClientCodec());
        /*fixme 消息聚合？原理？*/
        pl.addLast("aggregator", new HttpObjectAggregator(NettyConst.HTTP_AGGREGATE_LEN));
        /*解压缩 和服务端压缩配套 不必须*/
//        pl.addLast("decompressor", new HttpContentDecompressor());
        pl.addLast(new ClientBusiHandler());
    }
}
