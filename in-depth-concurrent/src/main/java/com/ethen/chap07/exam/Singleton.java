package com.ethen.chap07.exam;

/**
 * fixme 借助了类加载机制的线程安全
 */
public class Singleton {

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static Singleton instance = new Singleton();
    }
}
