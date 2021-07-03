package com.ethen.chap02;

/**
 * description: 对象优先在eden区分配
 * <p>
 * -XX:+PrintGCDetails
 * Enables printing of detailed messages at every GC. By default, this option is disabled.
 *
 * @author ethenyang@126.com
 * @since 2021/07/03
 */
public class EdenAllocation {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] obj1, obj2, obj3, obj4;
        obj1 = new byte[_1MB];
        obj2 = new byte[_1MB];
        obj3 = new byte[_1MB];
        obj4 = new byte[_1MB * 5];
    }
}
