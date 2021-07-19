package com.ethen.chap03;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    @AllArgsConstructor
    static class IncrementTask implements Runnable {
        private CASCounter counter;

        @Override
        public void run() {
            for (int k = 0; k < 1000; k++) {
                counter.increaseAndGet();
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        CASCounter casCounter = new CASCounter(new AtomicInteger());

        for (int j = 0; j < 10; j++) {
            new Thread(new IncrementTask(casCounter)).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.err.println("result：" + casCounter.getCount());
    }
}
