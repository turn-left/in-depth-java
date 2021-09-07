package com.ethen.netty.http.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ClientBusiHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取服务端应答消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse resp = (FullHttpResponse) msg;
        System.out.println("@接收到服务端应答@\n@header=" + resp.headers()
                + "\n@status=" + resp.status().toString() + "\n@content=" + resp.content().toString(StandardCharsets.UTF_8));
        resp.release();
    }

    /**
     * channel建立连接时发送数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI("/test/hello");
        String msg = "Hello,Netty!";
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1,
                HttpMethod.GET, uri.toASCIIString(),
                Unpooled.copiedBuffer(msg.getBytes(StandardCharsets.UTF_8)));
        // 请求头 客户端ip 长连接 消息体长度
        request.headers().set(HttpHeaderNames.HOST, HttpClient.HOST);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        // 发送请求数据，会经过编码handler处理
        ctx.writeAndFlush(request);
    }
}
