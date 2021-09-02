package com.ethen.frame.server;

import com.ethen.frame.vo.MessageType;
import com.ethen.frame.vo.MyHeader;
import com.ethen.frame.vo.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * netty服务端心跳应答handler
 */
@Slf4j
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyMessage req = (MyMessage) msg;
        if (req.getHeader() != null && req.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            // 回应心跳消息
            ctx.writeAndFlush(buildHeartBeatResp());
            // fixme 释放Buffer
            ReferenceCountUtil.release(msg);
        } else {
            // 向下一个inbound-handler传递
            ctx.fireChannelRead(msg);
        }
    }

    private MyMessage buildHeartBeatResp() {
        MyHeader header = new MyHeader();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        MyMessage resp = new MyMessage();
        resp.setHeader(header);
        return resp;
    }
}
