package com.ethen.chap01.vola;

import com.ethen.common.Person;

import java.util.concurrent.TimeUnit;

/**
 * @author ethenyang@126.com
 * @since 2021/07/11
 */
public class VolatileUse2 {
    static Person person = new Person("林俊杰", 41);
    static int age = 10;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                System.err.println(person);
                System.err.println(age);
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        person.setName("纠结伦");
        age = 23;
    }
}
