package com.ethen.chap01.vola;

import java.util.concurrent.TimeUnit;

public class VisibilityDemo implements Runnable {
    private static volatile boolean STATUS = true;

    public void run() {
        System.out.println("执行fun");
        while (STATUS) {
//            System.out.println("进入了循环");
        }
        System.err.println("退出循环！！！！");
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityDemo visibilityDemo = new VisibilityDemo();
        new Thread(visibilityDemo).start();
        TimeUnit.SECONDS.sleep(1);
        STATUS = false;
        TimeUnit.SECONDS.sleep(5);
        System.err.println("main is end");
    }
}