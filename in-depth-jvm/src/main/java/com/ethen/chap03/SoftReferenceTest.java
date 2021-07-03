package com.ethen.chap03;

import com.ethen.common.Constants;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * 软引用 -> 当虚拟机内存不够(将要抛出OOM异常时),清理该类型对象所在内存空间
 * <p>
 * JVM参数：
 * -Xms10m
 * -Xmx10m
 * -XX:+PrintGC
 *
 * @author ethenyang@126.com
 * @since 2021/07/03
 */
public class SoftReferenceTest {
    public static class User {
        private String name;
        private int id;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }


    public static void main(String[] args) {
        User user = new User(123, "tomcat"); // 强引用
        SoftReference<User> userSoft = new SoftReference<>(user);
        user = null; // 干掉强引用，确保只有弱引用
        System.err.println(userSoft.get());
        System.gc();// 进行强制gc
        System.err.println("after system gc ...");
        System.err.println(userSoft.get());

        // 往堆中填充数据，制造OOM，查看弱引用对象存活情况
        List<byte[]> list = new ArrayList<>();
        try {
            for (int i = 0; i < 1000; i++) {
                System.err.println(userSoft.get());
                byte[] bytes = new byte[Constants._1M];
                list.add(bytes);
            }
        } catch (Throwable e) {
            System.err.println("when exception happen: " + userSoft.get());
            e.printStackTrace();
        }
    }
}
