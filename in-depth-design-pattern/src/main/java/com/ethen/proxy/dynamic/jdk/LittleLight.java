package com.ethen.proxy.dynamic.jdk;

/**
 * 被代理类小明
 */
public class LittleLight implements People {
    @Override
    public void findObject() {
        System.err.println("我是小明，我是加瓦够，我没有时间找对象，哎呀~~~~~~~~~！");
    }
}
