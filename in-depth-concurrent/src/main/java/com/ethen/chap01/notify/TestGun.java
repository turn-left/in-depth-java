package com.ethen.chap01.notify;

import java.util.concurrent.TimeUnit;

/**
 * 测试 Gun
 *
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class TestGun {

    static class Loader extends Thread {
        private Gun gun;

        public Loader(Gun gun) {
            this.gun = gun;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.err.println("开始装弹。。。");
                    this.gun.load();
                    TimeUnit.MICROSECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Shooter extends Thread {
        private Gun gun;

        public Shooter(Gun gun) {
            this.gun = gun;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.err.println("开始射击。。。。");
                    this.gun.shoot();
                    TimeUnit.SECONDS.sleep(1);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Gun gun = new Gun();
        new Loader(gun).start();
        new Shooter(gun).start();

    }
}
