package com.ethen.netty.pkg.delimiter;

import com.ethen.netty.common.NettyConst;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class DelimiterServerHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取客户端发过来的数据
        ByteBuf req = (ByteBuf) msg;
        String content = req.toString(StandardCharsets.UTF_8);
        System.out.println("接收到客户端的请求消息：" + content + "，计数器：" + counter.incrementAndGet());
        String resp = "Hello," + content + "！欢迎来到Netty的世界！！！" + NettyConst.DEF_DELIMITER + NettyConst.DEF_DELIMITER;
        ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes(StandardCharsets.UTF_8)));
    }
}
