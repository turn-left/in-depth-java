package com.ethen.chap06.exam;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseThreadPool {
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_SIZE = 2 * CORE_SIZE;
    private static final long KEEP_ALIVE_SECONDS = 60;

    public static void main(String[] args) {
//        new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,)
    }
}
