package com.ethen.chap03;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCounter {
    private AtomicInteger counter;

    public CASCounter(AtomicInteger counter) {
        this.counter = counter;
    }

    public int getCount() {
        return counter.get();
    }

    public int increaseAndGet() {
        int count = getCount();
        boolean isSuccess = false;
        do {
            isSuccess = compareAndSwap(count, ++count);
        } while (!isSuccess);
        return count; // 注意返回值是否原子操作
    }

    private boolean compareAndSwap(int oldValue, int expectedValue) {
        return counter.compareAndSet(oldValue, expectedValue);
    }
}
