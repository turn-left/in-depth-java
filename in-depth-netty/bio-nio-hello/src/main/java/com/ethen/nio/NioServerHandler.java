package com.ethen.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioServerHandler implements Runnable {
    private volatile boolean started;
    private Selector selector;

    public NioServerHandler(int port) {
        try {
            // 创建选择器
            this.selector = Selector.open();
            // 创建 ServerSocketChannel
            final ServerSocketChannel ssc = ServerSocketChannel.open();
            // 配置为非阻塞模式
            ssc.configureBlocking(false);
            // 绑定端口
            ssc.socket().bind(new InetSocketAddress(NioConst.DEF_PORT));
            // 服务器启动向选择器注册感兴趣的监听事件->连接创建事件
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            // 刷新标记位
            this.started = true;
            System.err.println("NioServerHandler 服务已启动 ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.started = false;
    }

    @Override
    public void run() {
        while (started) {
            try {
                this.selector.select(1000);
                final Set<SelectionKey> keys = this.selector.keys();
                final Iterator<SelectionKey> it = keys.iterator();
                for (SelectionKey k = it.next(); it.hasNext(); k = it.next()) {
                    /** 标记为删除 {@link SelectionKey#isValid()} */
                    it.remove();
                    if (k.isValid()) continue;
                    if (k.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) k.channel();
                        // 建立连接
                        SocketChannel sc = ssc.accept();
                        System.out.println(sc + "====连接已建立====");
                        // 新建立的连接监听读事件
                        sc.register(this.selector, SelectionKey.OP_READ);
                        // 处理客户端发送的数据
                    } else if (k.isReadable()) {
                        handleRequest(k);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理业务逻辑
     * fixme 线程池处理？？？
     *
     * @param k SelectionKey
     * @throws IOException
     */
    private void handleRequest(SelectionKey k) throws IOException {
        SocketChannel sc = (SocketChannel) k.channel();
        ByteBuffer bf = ByteBuffer.allocate(1024);
        // 将channel中的数据写道buffer
        int len = sc.read(bf);
        if (len > 0) {
            bf.flip(); // 切换读模式
            byte[] bts = new byte[bf.remaining()];
            bf.get(bts);
            String req = new String(bts, Charset.defaultCharset());
            System.out.println("handleRequest 读取到客户端数据：" + req);
            String resp = NioConst.response(req);
            byte[] ret = resp.getBytes(StandardCharsets.UTF_8);
            ByteBuffer respBuf = ByteBuffer.allocate(ret.length);
            respBuf.put(ret);
            respBuf.flip();
            // 从buffer向channel中写数据
            sc.write(respBuf);
        } else {
            k.cancel();
            sc.close();
        }
    }
}
