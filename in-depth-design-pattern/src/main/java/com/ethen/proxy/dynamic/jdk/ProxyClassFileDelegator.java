package com.ethen.proxy.dynamic.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProxyClassFileDelegator {
    /**
     * 创建代理类的class文件
     *
     * @param klass 被代理类的Class对象
     */
    public static void createProxyClassFile(Class<?> klass) {
        try (FileOutputStream output = new FileOutputStream("$Proxy0.class")) {
            final byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class<?>[]{klass});
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
