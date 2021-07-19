package com.ethen.chap01.notify;

import java.util.concurrent.TimeUnit;

/**
 * 《wait/notify实现生产者和消费者程序》
 * 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，
 * 对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，生产者线程是一个压入线程，
 * 它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 * 请实现上面的程序。
 *
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class Gun {
    private int bullet;

    public static final int CAPACITY = 20;

    public synchronized void load() throws InterruptedException {
        while (bullet >= CAPACITY) {
            System.err.println("弹夹已满,暂停装弹！");
            wait();
        }
        bullet++;
        System.err.println("in capacity load bullet " + bullet);
        notifyAll();
    }

    public synchronized void shoot() throws InterruptedException {
        while (bullet <= 0) {
            System.err.println("弹夹已空，停止射击！");
            wait();
        }
        bullet--;
        System.err.println("in capacity shoot " + bullet);
        notifyAll();
    }

//    public synchronized void checkBullet() throws InterruptedException {
//        while (bullet >= 0 && bullet <= CAPACITY) {
//            System.out.println("checkBullet bullet=" + bullet);
//            notifyAll();
//            TimeUnit.SECONDS.sleep(1);
//        }
//    }
}
