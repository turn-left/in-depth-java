package com.ethen.proxy.dynamic.jdk;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * <p>
 * {@link => https://blog.csdn.net/luoyang_java/article/details/83543715}
 */
public class TestJdkProxy {
    public static void main(String[] args) {
        final LittleLight littleLight = new LittleLight();
        final People proxyInstance =
                (People) Proxy.newProxyInstance(
                        TestJdkProxy.class.getClassLoader(),
                        new Class[]{People.class},
                        new Parent(littleLight));
        proxyInstance.findObject();
    }
}
