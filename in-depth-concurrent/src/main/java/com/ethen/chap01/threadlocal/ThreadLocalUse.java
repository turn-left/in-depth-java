package com.ethen.chap01.threadlocal;

import com.ethen.common.Person;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal使用及原理
 * <p>
 * {@link 彻底搞懂threadLocal https://www.cnblogs.com/xzwblog/p/7227509.html}
 *
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class ThreadLocalUse {
    static ThreadLocal<Person> threadLocal = new ThreadLocal<Person>() {
        @Override
        protected Person initialValue() {
            // 初始new出了一个新的对象，深拷贝
            return new Person("tom-cruise", 39);
        }
    };


    public static void main(String[] args) throws InterruptedException {
        System.err.println(threadLocal.get());
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            final Person person = threadLocal.get();
            person.setName("周润发");
            System.err.println(Thread.currentThread().getName() + "--" + person + "-----" + System.identityHashCode(person));
        }).start();
        TimeUnit.SECONDS.sleep(1);
        System.err.println(threadLocal.get());
        System.err.println(Thread.currentThread().getName() + "-----" + System.identityHashCode(threadLocal.get()));
    }
}
