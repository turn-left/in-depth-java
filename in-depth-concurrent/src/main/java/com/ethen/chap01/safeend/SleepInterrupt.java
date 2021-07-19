package com.ethen.chap01.safeend;

import java.util.concurrent.TimeUnit;

/**
 * 休眠和中断
 *
 * @author ethenyang@126.com
 * @since 2021/07/10
 */
public class SleepInterrupt extends Thread {
    @Override
    public void run() {
        System.out.println("start i/o operation...");
        // 模拟IO操作
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("end i/o operation!!!");
        } catch (InterruptedException e) {
            System.err.println("thread interrupted i/o end not normal!release thread to pool!");
            e.printStackTrace();
        }
        final long l = System.currentTimeMillis();

        Thread.yield();
        System.err.println(Thread.currentThread().getName() + " yield end" + ",time:" + (System.currentTimeMillis() - l));
        System.err.println();
    }

    public static void main(String[] args) throws InterruptedException {
        final SleepInterrupt sleepInterrupt = new SleepInterrupt();
        sleepInterrupt.start();
        TimeUnit.SECONDS.sleep(1);
        sleepInterrupt.interrupt();
        while (true) ;
    }
}
