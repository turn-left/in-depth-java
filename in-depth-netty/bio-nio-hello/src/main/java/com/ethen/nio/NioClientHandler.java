package com.ethen.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NioClientHandler implements Runnable {
    private final String host;
    private final int port;

    public NioClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        // todo ... 2021/8/23
    }

    @Override
    public void run() {

    }
}
