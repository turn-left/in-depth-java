package com.ethen.netty.http.server;

import com.ethen.netty.common.NettyConst;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * fixme HTTP协议是如何解决TCP粘包半包处理？具体实现细节！
 * HTTP消息解码
 * HTTP消息编码
 * 消息报文压缩
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();
        /* 处理应答消息的编码 */
        pl.addLast("encoder", new HttpResponseEncoder());
        /* 处理请求消息的解码 fixme 粘包、拆包解决？？？ */
        pl.addLast("decoder", new HttpRequestDecoder());
        /* 聚合HTTP消息为一个完整报文 */
        pl.addLast("aggregator", new HttpObjectAggregator(NettyConst.HTTP_AGGREGATE_LEN));
        /* 应答报文压缩 非必要 */
//        pl.addLast("compressor", new HttpContentCompressor());
        pl.addLast(new ServerBusiHandler());
    }
}
