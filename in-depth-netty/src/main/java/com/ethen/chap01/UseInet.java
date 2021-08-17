package com.ethen.chap01;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class UseInet {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        InetAddress address = InetAddress.getByName("www.baidu.com");
        System.err.println(address);
        // --------------------------------------------------------------------------------------
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            System.out.println(networkInterfaces.nextElement());
        }
    }
}
