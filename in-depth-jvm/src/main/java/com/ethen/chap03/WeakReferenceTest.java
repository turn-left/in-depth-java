package com.ethen.chap03;

import com.ethen.common.User;

import java.lang.ref.WeakReference;

/**
 * description: �����÷���
 * JVM������
 * -Xms100m
 * -Xmx100m
 * -XX:+PrintGCDetails
 *
 * @author ethenyang@126.com
 * @since 2021/07/03
 */
public class WeakReferenceTest {

    public static void main(String[] args) {
        User user = new User(12345, "��ʮһ��");
        WeakReference<User> userWeakReference = new WeakReference<>(user);
        user = null;
        System.err.println(userWeakReference.get());
        System.gc();
        System.err.println("after system gc, " + userWeakReference.get());
    }
}
