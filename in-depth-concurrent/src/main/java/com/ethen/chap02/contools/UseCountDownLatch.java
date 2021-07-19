package com.ethen.chap02.contools;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使用闭锁模拟工程启动初始化工作
 * spring容器需要等初始化工作完成之后才能对外提供服务
 */
public class UseCountDownLatch {

    public static void main(String... args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        System.err.println("......Spring容器准备开始启动.......");
        new Thread(new AutowiredAndAopTask(countDownLatch)).start();
        new Thread(new InitDbPoolTask(countDownLatch)).start();
        countDownLatch.await(); // 主线程(spring核心线程)阻塞在这里
        System.err.println("Spring容器已就绪，开始对外提供服务!!!");
    }

    @AllArgsConstructor
    static class AutowiredAndAopTask implements Runnable {
        private CountDownLatch latch;

        @SneakyThrows
        @Override
        public void run() {
            System.out.println("start autowired task ...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("end autowired task !!!");
            if (latch != null) latch.countDown();
            System.out.println("start AOP task ...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("end AOP task !!!");
            if (latch != null) latch.countDown();
        }
    }

    @AllArgsConstructor
    static class InitDbPoolTask implements Runnable {
        private CountDownLatch latch;

        @Override
        public void run() {
            try {
                System.out.println("start implements db pool");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("end init db pool task.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (this.latch != null) {
                    System.err.println("countDownLatch执行扣减");
                    latch.countDown();
                }
            }
        }
    }
}
