package cn.enjoyedu.netbase;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UseInet {

    public static void main(String[] args) throws UnknownHostException {
//        InetAddress address = InetAddress.getByName("www.baidu.com");
//        System.out.println(address);
//
//        InetAddress address2 =  InetAddress.getByName("124.232.170.22");
//        System.out.println(address2.getHostName());

        InetAddress[] allByName = InetAddress.getAllByName("www.baidu.com");
        for(InetAddress addr:allByName ){
            System.out.println(addr);
        }

        //{192.168.56.1};
        byte[] bytes = {(byte)192,(byte)168,56,1};
        InetAddress byAddress = InetAddress.getByAddress(bytes);
    }
}
