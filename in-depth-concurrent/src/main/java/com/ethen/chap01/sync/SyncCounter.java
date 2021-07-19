package com.ethen.chap01.sync;

import java.util.concurrent.TimeUnit;

/**
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class SyncCounter {
    private int count;
    private int seed;

    public void incr() {
        synchronized (wrongLock) {
            this.count++;
        }
        wrongLock++;
    }

    public void incr2() {
        synchronized (lock) {
            seed++;
        }
    }

    @Override
    public String toString() {
        return "SyncCounter{" +
                "count=" + count +
                ",seed=" + seed +
                '}';
    }

    static class Cal extends Thread {
        private SyncCounter counter;

        Cal(SyncCounter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.incr();
                counter.incr2();
            }
        }
    }

    private Integer wrongLock = 1; // 错误的加锁
    private Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        final SyncCounter syncCounter = new SyncCounter();
        for (int i = 0; i < 5; i++) {
            new Cal(syncCounter).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.err.println(syncCounter);
    }
}
