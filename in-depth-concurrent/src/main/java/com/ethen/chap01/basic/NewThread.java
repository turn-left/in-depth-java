package com.ethen.chap01.basic;

import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author ethenyang@126.com
 * @since 2021/07/10
 */
public class NewThread {

    static class UseThread extends Thread {
        @Override
        public void run() {
            // 执行自己的业务逻辑
            System.err.println("UseThread start task ...");
            System.err.println("UseThread task competed ...");
        }
    }

    static class UseRunnable implements Runnable {
        @Override
        public void run() {
            System.err.println("UseRunnable................");
        }
    }

    public static void main(String[] args) {
        UseThread useThread = new UseThread();
        Runnable runnable = new UseRunnable();
        useThread.start();
        new Thread(runnable).start();
    }
}
