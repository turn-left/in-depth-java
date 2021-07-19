package com.ethen.common;

import java.util.concurrent.TimeUnit;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * <p>
 * 类说明：线程休眠辅助工具类
 */
public class SleepTools {

    /**
     * 按秒休眠
     *
     * @param seconds 秒数
     */
    public static final void second(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 按毫秒数休眠
     *
     * @param seconds 毫秒数
     */
    public static final void ms(int seconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 打发时间
     *
     * @param ms 毫秒数
     */
    public static final void spendTime(long ms) {
        long cursor = System.currentTimeMillis();
        while (System.currentTimeMillis() <= (cursor + ms)) ;
        System.out.println(Thread.currentThread().getId() + " spend time " + ms + " millis");
    }
}
