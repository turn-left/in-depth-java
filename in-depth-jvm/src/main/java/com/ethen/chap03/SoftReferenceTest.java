package com.ethen.chap03;

import com.ethen.common.Constants;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * ������ -> ��������ڴ治��(��Ҫ�׳�OOM�쳣ʱ),��������Ͷ��������ڴ�ռ�
 * <p>
 * JVM������
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
        User user = new User(123, "tomcat"); // ǿ����
        SoftReference<User> userSoft = new SoftReference<>(user);
        user = null; // �ɵ�ǿ���ã�ȷ��ֻ��������
        System.err.println(userSoft.get());
        System.gc();// ����ǿ��gc
        System.err.println("after system gc ...");
        System.err.println(userSoft.get());

        // ������������ݣ�����OOM���鿴�����ö��������
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
