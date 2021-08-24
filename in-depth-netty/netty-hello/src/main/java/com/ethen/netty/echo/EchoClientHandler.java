package com.ethen.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Scanner;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 当通道活跃后 做事情
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Netty!!!Current " + LocalDateTime.now(), StandardCharsets.UTF_8));
    }


    /**
     * 读取到网络数据后 进行业务处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.err.println("EchoClientHandler 接收到服务端数据：" + msg.toString(StandardCharsets.UTF_8));
        Scanner s = new Scanner(System.in);
        while (s.hasNext()) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(s.nextLine(),StandardCharsets.UTF_8));
        }
    }
}
