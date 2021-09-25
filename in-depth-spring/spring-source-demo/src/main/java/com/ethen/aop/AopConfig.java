package com.ethen.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启用spring AOP
 * 注意需要能被component-scan扫描到！
 * <p>
 * Spring AOP 报错：com.sun.proxy.$Proxy xxx cannot be cast to xxx 解决方案
 * {@link -> https://blog.csdn.net/zhouzhiwengang/article/details/50884353}
 * <p>
 * Spring AOP 方法嵌套问题解决方案
 * {@link -> https://blog.csdn.net/hl_java/article/details/79445799}
 */
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Configuration
public class AopConfig {
    @Bean
    public AudienceAspect audienceAspect() {
        return new AudienceAspect();
    }
}
