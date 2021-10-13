package com.ethen.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Parent implements InvocationHandler {
    private final People people;

    public Parent(People people) {
        this.people = people;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置增强
        before();

        method.invoke(people, args);

        // 后置增强
        after();
        return proxy;
    }

    public void before() {
        System.err.println("====================我们是LittleLight的父母，帮小明找对象结婚====================");
    }

    public void after() {
        System.err.println("====================我们是LittleLight的父母，帮小明带娃====================");
    }
}
