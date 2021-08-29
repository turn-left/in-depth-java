package com.ethen.netty.pkg.linebase;

import com.ethen.netty.common.NettyConst;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class LineBaseClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private AtomicInteger counterClient = new AtomicInteger();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String resp = msg.toString(StandardCharsets.UTF_8);
        System.out.println("接收到服务端响应：" + resp + ",客户端计数器：" + counterClient.incrementAndGet());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String requestStr = NettyConst.XX_MASTER_5 + System.getProperty("line.separator");
        byte[] bytes = requestStr.getBytes(StandardCharsets.UTF_8);
        for (int k = 0; k < 10; k++) {
            ByteBuf byteBuf = Unpooled.buffer(bytes.length);
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(byteBuf);
        }
    }
}
