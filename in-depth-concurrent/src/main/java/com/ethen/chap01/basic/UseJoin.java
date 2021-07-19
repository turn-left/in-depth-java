package com.ethen.chap01.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author ethenyang@126.com
 * @since 2021/07/10
 */
public class UseJoin {
    // 模拟lison打饭
    static class Lison extends Thread {
        private Thread th;

        public void setTh(Thread th) {
            this.th = th;
        }

        @Override
        public void run() {
            try {
                System.err.println("lison开始排队打饭..." + Thread.currentThread().getName());
                if (th != null) th.join();
                TimeUnit.SECONDS.sleep(2);
                System.err.println("lison打饭完毕！！！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Goddess extends Thread {
        private Thread t;

        public void setT(Thread t) {
            this.t = t;
        }

        @Override
        public void run() {
            try {
                System.err.println("Goddess 开始打饭..." + Thread.currentThread().getName());
                if (t != null) t.join();
                TimeUnit.SECONDS.sleep(2);
                System.err.println("Goddess 打饭完毕！！！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Lison lison = new Lison();
        final Goddess goddess = new Goddess();
        final Thread boy = new Thread(() -> {
            System.err.println("goddess's boyfriend 开始打饭！" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
                System.err.println("goddess's boyfriend 打饭完成。" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        lison.setTh(goddess);
        goddess.setT(boy);
        lison.start();
        goddess.start();
        boy.start();
    }
}
