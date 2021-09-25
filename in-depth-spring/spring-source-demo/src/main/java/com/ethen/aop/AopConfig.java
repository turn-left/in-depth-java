package com.ethen.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启用spring AOP
 * 注意需要能被component-scan扫描到！
 * <p>
 * Spring aop报错：com.sun.proxy.$Proxy xxx cannot be cast to xxx
 * {@link -> https://blog.csdn.net/zhouzhiwengang/article/details/50884353}
 */
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Configuration
public class AopConfig {
    @Bean
    public AudienceAspect audienceAspect() {
        return new AudienceAspect();
    }
}
