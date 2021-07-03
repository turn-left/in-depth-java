package com.ethen.chap01;

/**
 * description:
 *
 * @author ethenyang@126.com
 * @since 2021/06/30
 */
public class JavaStack {
    public static void main(String[] args) {
        final JavaStack javaStack = new JavaStack();
        javaStack.king(1000);
    }

    public int king(int money) {
        return money - 100;
    }
}
