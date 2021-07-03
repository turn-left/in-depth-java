package com.ethen.chap01;

/**
 * description:
 *
 * @author ethenyang@126.com
 * @since 2021/07/01
 */
public class StackOverflowTest {
    static long counter = 0L;

    public void hello() {
        counter++;
        System.err.println(counter);
        hello();
    }

    public static void main(String[] args) {
        final StackOverflowTest statckOverflowTest = new StackOverflowTest();
        statckOverflowTest.hello();
    }
}
