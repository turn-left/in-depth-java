package com.ethen.chap06.exam;

import com.ethen.common.SleepTools;

public class TestDeadLock {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void getLock12() {
        synchronized (lock1) {
            System.out.println("get lock1...");
            SleepTools.spendTime(50);
            synchronized (lock2) {
                System.out.println("get lock2...");
            }
        }
    }

    public void getLock21() {
        synchronized (lock2) {
            System.out.println("get lock2...");
            synchronized (lock1) {
                System.out.println("get lock1...");
            }
        }
    }

    public static void main(String[] args) {
        TestDeadLock testDeadLock = new TestDeadLock();
        new Thread(testDeadLock::getLock12).start();
        SleepTools.spendTime(5);
        testDeadLock.getLock21();
    }
}
