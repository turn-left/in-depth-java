package com.ethen.spring.util;

public class TimeUtil {
    public static void spend(long millis) {
        assert millis >= 0;
        long deadline = System.currentTimeMillis() + millis;
        // CPU空转 不释放锁
        while (deadline >= System.currentTimeMillis()) {
        }

    }
}
