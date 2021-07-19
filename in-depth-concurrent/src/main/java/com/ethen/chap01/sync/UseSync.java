package com.ethen.chap01.sync;

/**
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class UseSync {
    private Object lockObj = new Object();

    public UseSync() {
        System.err.println("invoke new UseSync()...");
    }

    public synchronized void add(int a, int b) {
        int result = a + b;
        System.err.println("add result:" + result);
    }

    public synchronized void multiply(int a, int b) {
        int result2 = a * b;
        System.err.println("multiply result2:" + result2);
    }

    public void pow(double a, double n) {
        synchronized (lockObj) {
            double result3 = Math.pow(a, n);
            System.err.println("pow result3:" + result3);
        }
    }

    public static void main(String[] args) {
        final UseSync useSync = new UseSync();
        final UseSync useSync2 = new UseSync();
        useSync.add(23, 15);
        useSync.multiply(15, 65);
    }
}
