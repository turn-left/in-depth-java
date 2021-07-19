package com.ethen.chap01.notify;

import java.util.concurrent.TimeUnit;

/**
 * notify wait机制
 * <p>
 * 模拟快递实体类
 *
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class Express {
    private int km; // 当前公里数
    private String site; // 目标地点

    public static final String TARGET_CITY = "guangzhou";

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /**
     * 运输历程发生变化通知其他线程
     *
     * @param km
     */
    public synchronized void changeKm(int km) {
        this.km = km;
        notifyAll();
    }

    /**
     * 地点发生变化通知
     *
     * @param site
     */
    public synchronized void changeSite(String site) {
        this.site = site;
        notifyAll();
    }

    public synchronized void checkKm(int km) {
        while (this.km < km) {
            try {
                wait(); // 线程等待，并释放对象锁
                System.err.println("current checkKm thread " + Thread.currentThread().getName() + " is notified.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println("the km is " + this.km + ",i will record it to database!");
    }

    public synchronized void checkSite() {
        while (!this.site.equals(TARGET_CITY)) {
            try {
                wait();
                System.err.println("current checkSite thread " + Thread.currentThread().getName() + " has been notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println("the site is " + this.site + ",i'll be call user!");
    }
}
