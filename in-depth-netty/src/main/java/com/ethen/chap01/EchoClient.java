package com.ethen.chap01;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket();
        final InetSocketAddress address = new InetSocketAddress("localhost", 10001);
        socket.connect(address);
        try (
                final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ) {
            // 从控制台读取
            final Scanner scanner = new Scanner(System.in);
            for (String s = scanner.nextLine(); scanner.hasNext(); s = scanner.nextLine()) {
                oos.writeUTF(s);
                oos.flush();
                System.err.println(ois.readUTF());
            }
//            oos.writeUTF("Mark!");
//            oos.flush();
//            System.err.println("收到服务端回复[" + ois.readUTF() + "]");
            System.err.println(124141);
        } finally {
            if (socket != null) socket.close();
        }
    }
}
