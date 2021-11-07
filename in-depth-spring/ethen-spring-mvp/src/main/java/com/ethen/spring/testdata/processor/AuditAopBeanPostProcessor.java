package com.ethen.spring.testdata.processor;

import com.ethen.spring.annotation.Component;
import com.ethen.spring.beans.BeanPostProcessor;
import com.ethen.spring.testdata.bean.RegisterService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class AuditAopBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // fixme 为RegisterService生成代理
        if ("registerService".equals(beanName)) {
            InvocationHandler handler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    final long start = System.currentTimeMillis();
                    final Object result = method.invoke(bean, args);
                    final long duration = System.currentTimeMillis() - start;
                    System.err.println("AuditAopBeanPostProcessor:" + method + " invoked cost: " + duration + " millis");
                    return result;
                }
            };
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), handler);
        }
        return bean;
    }
}
