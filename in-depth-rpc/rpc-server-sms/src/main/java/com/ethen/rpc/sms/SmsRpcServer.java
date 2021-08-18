package com.ethen.rpc.sms;

import com.ethen.rpc.base.RpcServerFrame;
import com.ethen.rpc.remote.SendSms;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 发布SendSms服务
 */
@Component
public class SmsRpcServer implements BeanPostProcessor {
    private final RpcServerFrame rpcServerFrame;

    @Autowired
    public SmsRpcServer(RpcServerFrame rpcServerFrame) {
        this.rpcServerFrame = rpcServerFrame;
    }

    /**
     * bean初始化完成之后执行操作
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        int port = 8778 + new Random().nextInt(100);
        // fixme 启动SendSms服务
        rpcServerFrame.start(SendSms.class.getName(), "127.0.0.1", port);
        return bean;
    }
}
