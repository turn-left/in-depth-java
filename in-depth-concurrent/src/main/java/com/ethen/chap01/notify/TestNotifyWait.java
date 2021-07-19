package com.ethen.chap01.notify;

import java.util.concurrent.TimeUnit;

/**
 * 模拟快递在一个节点修改当货物途径点
 * 到达目的地之后通知客户收件
 *
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class TestNotifyWait {
    public static Express express = new Express(0, "Beijing");

    // 另起线程CheckSite
    static class CheckSite extends Thread {
        @Override
        public void run() {
            express.checkSite();
        }
    }

    // 另起线程CheckKm
    static class CheckKm extends Thread {
        @Override
        public void run() {
            express.checkKm(300);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 2; i++) {
            new CheckSite().start();
            new CheckKm().start();
        }
        TimeUnit.SECONDS.sleep(1);
        express.changeKm(500);
        express.changeSite("guangzhou");
    }

}
