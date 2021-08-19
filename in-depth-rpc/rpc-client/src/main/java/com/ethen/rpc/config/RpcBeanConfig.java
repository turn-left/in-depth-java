package com.ethen.rpc.config;

import com.ethen.rpc.base.RpcClientFrame;
import com.ethen.rpc.remote.SendSms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcBeanConfig {
    @Value("${remote.sms.host}")
    private String host;

    @Value("${remote.sms.port}")
    private int port;

    @Bean
    public SendSms sendSms(RpcClientFrame sendSmsRpc) {
        return sendSmsRpc.getRemoteProxyObject(SendSms.class);
    }

    @Bean
    public RpcClientFrame sendSmsRpc() {
        final RpcClientFrame rpcClientFrame = new RpcClientFrame(host, port);
        return rpcClientFrame;
    }
}
