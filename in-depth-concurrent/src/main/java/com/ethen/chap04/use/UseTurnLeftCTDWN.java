package com.ethen.chap04.use;

import com.ethen.chap04.self.CountDown;
import com.ethen.chap04.self.TurnLeftCountDownLatch;
import com.ethen.common.SleepTools;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

public class UseTurnLeftCTDWN {
    static final int COUNT_NUM = 5;

    @AllArgsConstructor
    static class CountTask implements Runnable {
        private CountDown countDown;

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "-start task......");
            SleepTools.spendTime((long) (Math.random() * 5000));
            if (countDown != null) countDown.countDown();
            System.out.println(Thread.currentThread() + "-count down........" + countDown.getCount());
            SleepTools.spendTime((long) (Math.random() * 5000));
            System.out.println(Thread.currentThread() + "-end task........");
        }
    }

    @AllArgsConstructor
    static class AwaitTask implements Runnable {
        private CountDown countDown;

        @SneakyThrows
        @Override
        public void run() {
            System.err.println(Thread.currentThread() + "-执行await()........");
            countDown.wait();
            System.err.println(Thread.currentThread() + "-执行完毕............");
        }
    }

    public static void main(String[] args) {
        TurnLeftCountDownLatch latch = new TurnLeftCountDownLatch(COUNT_NUM);
        for (int k = 0; k < COUNT_NUM; k++) {
            new Thread(new CountTask(latch)).start();
        }
        new Thread(new AwaitTask(latch)).start();
        System.err.println(Thread.currentThread() + "-执行await().........");
        latch.await();
        System.err.println(Thread.currentThread() + "-执行结束.............");


    }
}
