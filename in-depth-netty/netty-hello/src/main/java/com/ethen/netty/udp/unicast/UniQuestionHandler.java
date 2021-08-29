package com.ethen.netty.udp.unicast;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

public class UniQuestionHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String content = msg.content().toString(CharsetUtil.UTF_8);
        if (!StringUtil.isNullOrEmpty(content) && content.startsWith(UniAnswerSide.ANSWER)) {
            System.out.println("UniQuestionHandler接收到消息：" + content);
            ctx.close(); // fixme 2021/8/29 what is this mean???
        }
    }
}
