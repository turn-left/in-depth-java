package com.ethen.chap02.contools;

import com.ethen.common.ArrayCreator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier使用demo
 */
public class UseCyclicBarrier {
    static long addR = 0;
    static long multiplyR = 0;

    public static void main(String[] args) {
        Runnable action = () -> {
            System.err.println(Thread.currentThread().getName() + "addR=" + addR + ",multiplyR=" + multiplyR);
        };
        CyclicBarrier barrier = new CyclicBarrier(3, action);
        int[] array = ArrayCreator.getArray(50);
        MultiplyTask multiplyTask = new MultiplyTask(array, barrier);
        AddTask addTask = new AddTask(array, barrier);
        new Thread(multiplyTask).start();
        new Thread(addTask).start();
    }

    @AllArgsConstructor
    static class MultiplyTask implements Runnable {
        private int[] source;
        private CyclicBarrier barrier;

        @SneakyThrows
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            System.err.println("start MultiplyTask ...current:" + start);
            if (source == null) throw new IllegalArgumentException("source must not be empty.");
            long result = 1;
            for (int i = 0; i < source.length; i++) {
                result *= source[i];
                TimeUnit.MILLISECONDS.sleep(20);
            }
            multiplyR = result;
            System.err.println("end MultiplyTask result:" + result + " ,cost:" + (System.currentTimeMillis() - start));
            if (barrier != null) {
                System.err.println(Thread.currentThread().getName() + "开始进入屏障...");
                barrier.await();
                System.err.println(Thread.currentThread().getName() + "突破屏障哈哈!!!");
            }
        }
    }

    @AllArgsConstructor
    static class AddTask implements Runnable {
        private int[] source;
        private CyclicBarrier barrier;

        @SneakyThrows
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            System.err.println("start AddTask ...current:" + start);
            if (source == null) throw new IllegalArgumentException("source must not be empty.");
            int result = 0;
            for (int i = 0; i < source.length; i++) {
                result += source[i];
                TimeUnit.MILLISECONDS.sleep(5);
            }
            addR = result;
            System.err.println("end AddTask result:" + result + ",cost:" + (System.currentTimeMillis() - start));
            if (barrier != null) {
                System.err.println(Thread.currentThread().getName() + "开始进入屏障...");
                barrier.await();
                System.err.println(Thread.currentThread().getName() + "突破屏障哈哈!!!");
            }
        }
    }
}
