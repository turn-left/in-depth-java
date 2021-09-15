package com.ethen.server;

import com.ethen.handler.SecureChatServerInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * 安全的webSocket服务器引导类
 */
public class SecureChatServer extends ChatServer {
    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createChannelInitializer(ChannelGroup channelGroup) {
        return new SecureChatServerInitializer(channelGroup, context);
    }

    public static void main(String[] args) throws CertificateException, SSLException {
        if (args.length != 1) {
            System.err.println("Please give port as argument!");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        final SelfSignedCertificate cert = new SelfSignedCertificate();
        SslContext sslContext = SslContext.newServerContext(cert.certificate(), cert.privateKey());
        final ChatServer server = new SecureChatServer(sslContext);
        final ChannelFuture future = server.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread(server::destroy));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
