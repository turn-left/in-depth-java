package com.ethen.chap04;

/**
 * description: æ≤Ã¨∑÷≈… demo
 *
 * @author ethenyang@126.com
 * @since 2021/07/05
 */
public class StaticDispatch {

    public static abstract class Human {
    }

    public static class Man extends Human {
    }

    public static class Woman extends Human {
    }

    public void sayHello(Human human) {
        System.err.println("hello,human!");
    }

    public void sayHello(Man man) {
        System.err.println("hello,gentleman!");
    }

    public void sayHello(Woman woman) {
        System.err.println("hello,lady@@!");
    }

    public static void main(String[] args) {
        final Man man = new Man();
        final Woman woman = new Woman();
        final StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello(man);
        staticDispatch.sayHello(woman);
    }
}
