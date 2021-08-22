package com.ethen.rpc.base;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * 解决RPC通信问题-通用组件
 */
public class RpcServerFrame {
    private final ServiceRegister register;
    private final ExecutorService pool;

    public RpcServerFrame(ServiceRegister register, ExecutorService pool) {
        this.register = register;
        this.pool = pool;
    }

    /**
     * 启动rpc服务
     *
     * @param serviceName
     * @param host
     * @param port
     */
    public void startService(String serviceName, String host, int port) {
        try {
            final ServerSocket server = new ServerSocket();
            server.bind(new InetSocketAddress(host, port));
            System.err.println("=>>>>>>>>服务启动=>>>>>>>>主机:%s,端口:%d");

            while (true) {
                // 接收客户端请求(TCP三次握手建立连接)
                final Socket accept = server.accept();
                final Class<?> serviceImpl = register.getLocalService(serviceName);
                // fixme 分配线程与客户端交互
                pool.execute(new ServerTask(accept, serviceImpl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一次socket业务处理
     * <p>
     * 方法调用的几要素
     * 1.业务方法所在实例
     * 2.具体方法
     * 3.方法所需参数类型
     * 4.具体参数值
     */
    static class ServerTask implements Runnable {
        private final Socket socket;
        private final Class<?> serviceImpl;

        public ServerTask(Socket socket, Class<?> serviceImpl) {
            this.socket = socket;
            this.serviceImpl = serviceImpl;
        }

        // 业务处理
        @Override
        public void run() {
            try (
                    InputStream in = socket.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(in);
                    OutputStream out = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(out);
            ) {
                // 方法所在接口名
                final String serviceName = ois.readUTF();
                // Method名称
                final String methodName = ois.readUTF();
                // 方法类型数组
                final Class<?>[] paramTypes = (Class<?>[]) ois.readObject();
                // 方法参数值
                final Object[] params = (Object[]) ois.readObject();
                // 通过反色调用接口
                final Method serviceMethod = serviceImpl.getDeclaredMethod(methodName, paramTypes);
                final Object result = serviceMethod.invoke(serviceImpl.newInstance(), params);
                oos.writeObject(result);
                oos.flush();
            } catch (IOException | ClassNotFoundException | NoSuchMethodException
                    | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
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
