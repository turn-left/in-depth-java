package com.ethen.frame.server;

import com.ethen.frame.vo.MessageType;
import com.ethen.frame.vo.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Netty服务端处理登录握手
 */
@Slf4j
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    /**
     * 缓存已登录握手成功的节点
     * 此处仅作为demoV1.0 fixme 1.单点问题 2.缓存清除问题
     */
    static Map<String, Object> nodeCache = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyMessage req = (MyMessage) msg;
        if (req.getHeader() != null && req.getHeader().getType() == MessageType.LOGIN_REQ.value()) {
            // todo 重复登录处理
            // todo 登录逻辑
        } else {
            // 不属于登录握手业务，则将消息向后传递
            ctx.fireChannelRead(msg);
        }
    }
}
