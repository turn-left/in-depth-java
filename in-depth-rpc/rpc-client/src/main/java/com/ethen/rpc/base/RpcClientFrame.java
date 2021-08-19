package com.ethen.rpc.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * rpc框架的客户端代理部分
 */
public class RpcClientFrame {
    private final String host;

    private final int port;

    public RpcClientFrame(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 获取远端代理对象
     *
     * @param <T>
     * @return T
     */
    public <T> T getRemoteProxyObject(final Class<?> serviceInterface) {
        final InetSocketAddress address = new InetSocketAddress(host, port);
        final Object proxyInstance = Proxy.newProxyInstance(
                serviceInterface.getClassLoader(),
                new Class[]{serviceInterface},
                new DynProxy(address, serviceInterface));
        return (T) proxyInstance;
    }

    /**
     * JDK动态代理
     */
    static class DynProxy implements InvocationHandler {
        private final InetSocketAddress address;
        private final Class<?> serviceInterface;

        public DynProxy(InetSocketAddress address, Class<?> serviceInterface) {
            this.address = address;
            this.serviceInterface = serviceInterface;
        }

        /**
         * 远程调用
         *
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            final Socket socket = new Socket();
            socket.connect(address);
            try (
                    InputStream in = socket.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(in);
                    OutputStream out = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(out);
            ) {
                // 方法所在接口名
                oos.writeUTF(serviceInterface.getName());
                // Method名称
                oos.writeUTF(method.getName());
                // 方法类型数组
                oos.writeObject(method.getParameterTypes());
                // 方法参数值
                oos.writeObject(args);
                // 从缓存冲刷
                oos.flush();
                return ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
