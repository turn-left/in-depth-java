package com.ethen.chap01.vola;

import java.util.concurrent.TimeUnit;

/**
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class VolatileUseCase {
    private static boolean isExit;


    static class Work implements Runnable {
        @Override
        public void run() {
            System.err.println("running.........");
            while (!isExit) ;
            System.err.println("isExit..........");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Work()).start();
        TimeUnit.SECONDS.sleep(1);
        isExit = true;
        TimeUnit.SECONDS.sleep(2);
        System.err.println("main is end");
    }
}
