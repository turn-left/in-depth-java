package com.ethen.netty.http.server;

import com.ethen.netty.common.RespConstant;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 简易HTTP业务处理handler
 */
public class BusinessHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当连接建立时调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("====与客户端建立连接===>" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 通过netty内置HTTP解码器(某个入站handler)将入站消息解码
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        System.out.println("@http@header:" + httpRequest.headers());
        String uri = httpRequest.uri();
        HttpMethod method = httpRequest.method();
        String body = httpRequest.content().toString(CharsetUtil.UTF_8);
        System.out.println("接收到请求路径：" + uri + "请求方法：" + method.name() + "请求体：" + body);

        try {
            if (HttpMethod.GET.equals(method)) {
                // 处理GET请求
                send(ctx, HttpResponseStatus.OK, RespConstant.getNews() + LocalDateTime.now());
            } else if (HttpMethod.POST.equals(method)) {
                //处理POST请求
                send(ctx, HttpResponseStatus.CREATED, RespConstant.getNews());
            } else {
                // OPTION HEAD PUT DELETE 敬请期待 ...
                throw new UnsupportedOperationException("敬请期待~");
            }
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException)
                throw e;
            e.printStackTrace();
        } finally {
            httpRequest.release();
        }
    }

    /**
     * 响应HTTP消息
     *
     * @param ctx     handler上下文容器
     * @param status  HTTP状态码
     * @param content 消息内容
     */
    private void send(ChannelHandlerContext ctx, HttpResponseStatus status, String content) {
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer(content.getBytes(StandardCharsets.UTF_8)));
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
