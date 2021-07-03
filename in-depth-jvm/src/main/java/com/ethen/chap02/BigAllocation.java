package com.ethen.chap02;

/**
 * description: 大对象直接进入老年代
 * <p>
 * -Xms20m 堆内存初始大小20MB
 * -Xmx20m 堆内存最大值为20MB
 * -Xmn10m 新生代内存设置10MB
 * -Xss5m 设置Java线程堆栈大小为5MB
 * <p>
 * -XX:+PrintGCDetails
 * -XX:PretenureSizeThreshold=2m
 * -XX:+UseSerialGC
 *
 * @author ethenyang@126.com
 * @since 2021/07/03
 */
public class BigAllocation {
    private static final int _1MB = 1024 * 1024; //1M的大小

    // * 大对象直接进入老年代(Eden  2m  +1 )
    public static void main(String[] args) {
        byte[] b1, b2, b3;
        b1 = new byte[_1MB]; //这个对象在eden区
        b2 = new byte[_1MB]; //这个对象在eden区
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
    }
}
