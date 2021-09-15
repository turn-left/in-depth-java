package com.ethen.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 当客户端调用/ws任务是发起WebSocket连接
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUri;
    private final static File INDEX;

    static {
        try {
            URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
            String path = location.toURI() + "index.html";
            path = !path.contains("file") ? path : path.substring(5);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("unable to load index.html", e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 1.非HTTP请求，向后转发
        if (this.wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain()); // fixme retain()了解一下？
        } else {
            // 2.HTTP100处理
            if (HttpUtil.is100ContinueExpected(request)) {
                this.sendHttp100Resp(ctx);
            }
            // 3.返回index.html
            RandomAccessFile file = new RandomAccessFile(INDEX, "r"); // fixme RandomAccessFile了解一下？
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            // 4.处理keep-alive请求
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            // 5.写response
            ctx.write(response);
            // 6.写文件
            if (ctx.pipeline().get(SslHandler.class) == null) {
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            // 7.写响应消息结束符号 并冲刷消息
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            // 8.fixme 如果请求不是keep-alive，写操作完毕之后关闭channel
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }

    }

    private void sendHttp100Resp(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
}
