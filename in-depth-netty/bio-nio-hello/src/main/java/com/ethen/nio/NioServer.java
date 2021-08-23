package com.ethen.nio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO服务端
 */
public class NioServer {
    public static void main(String[] args) {
        final ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(new NioServerHandler(NioConst.DEF_PORT));
    }
}
