package com.ethen.util;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
    /**
     * CPU空轮询消耗时间，线程不休眠
     *
     * @param millis 毫秒数
     */
    public static void spend(long millis) {
        long deadline = System.currentTimeMillis() + millis;
        while (System.currentTimeMillis() < deadline) ;
        System.err.println("TimeUtil spend " + millis + "milliseconds");
    }

    /**
     * 通过休眠消耗时间，让出CPU执行权，不是放锁
     *
     * @param millis 毫秒数
     */
    @SneakyThrows
    public static void sleep(long millis) {
        TimeUnit.MILLISECONDS.sleep(millis);
    }
}
