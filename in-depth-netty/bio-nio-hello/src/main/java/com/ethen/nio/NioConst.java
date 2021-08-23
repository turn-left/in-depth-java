package com.ethen.nio;

public interface NioConst {
    String DEF_HOST = "127.0.0.1";
    int DEF_PORT = 9999;

    static String response(String msg) {
        return "Hello," + msg + "!This is java nio response!";
    }
}
