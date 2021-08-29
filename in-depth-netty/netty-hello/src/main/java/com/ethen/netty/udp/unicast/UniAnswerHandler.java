package com.ethen.netty.udp.unicast;

import com.ethen.netty.common.NettyConst;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

public class UniAnswerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String content = msg.content().toString(CharsetUtil.UTF_8);
        System.out.println("接收到UDP消息：" + content + " raw: " + msg);
        // 应答
        if (UniQuestionSide.QUESTION.equals(content)) {
            ByteBuf buf = Unpooled.copiedBuffer(nextQuote().getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(
                    new DatagramPacket(buf, msg.sender())
            );
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    private String nextQuote() {
        return UniAnswerSide.ANSWER + NettyConst.POEM_DICTIONARY[(int) (Math.random() * 4)];
    }
}
