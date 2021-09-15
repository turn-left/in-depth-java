package com.ethen.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class SecureChatServerInitializer extends ChatServerInitializer {
    public final SslContext sslContext;

    public SecureChatServerInitializer(ChannelGroup group, SslContext context) {
        super(group);
        this.sslContext = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        SSLEngine engine = sslContext.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        ch.pipeline().addLast(new SslHandler(engine));
    }
}
