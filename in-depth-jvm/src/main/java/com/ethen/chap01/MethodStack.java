package com.ethen.chap01;

/**
 * description: jvm为什么要用栈这种数据结构执行方法？？
 *
 * @author ethenyang@126.com
 * @since 2021/07/01
 */
public class MethodStack {
    public void methodA() {
        System.err.println("开始执行 methodA...");
        methodB();
        System.err.println("结束执行 methodA!!!");
    }

    private void methodB() {
        System.err.println("开始执行 methodB...");
        methodC();
        System.err.println("结束执行 methodB!!!");
    }

    private void methodC() {
        System.err.println("开始执行 methodC...");
        System.err.println("结束执行 methodC!!!");
    }

    public static void main(String[] args) {
        final MethodStack methodStack = new MethodStack();
        methodStack.methodA();
    }
}
