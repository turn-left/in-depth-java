package com.ethen.chap07.exam;

/**
 * 问题分析:双重检查真的安全吗？？
 * {@linkplain volatile 关键字}
 */
public class Singleton01 {
    @Deprecated
    private static Singleton01 instance02;

    private volatile static Singleton01 instance;

    // 私有化构造 -> 反射突破private除外
    private Singleton01() {
    }

    public static Singleton01 getInstance() {
        if (instance == null) {
            synchronized (Singleton01.class) {
                if (instance == null) {
                    // 分配内存空间
                    // 初始化内存
                    // 将instance指向内存空间
                    instance = new Singleton01();
                }
            }
        }
        // fixme 如果不加volatile 存在风险：instance未初始化完成即开始对外提供服务！！！
        return instance;
    }
}
