package com.ethen.common;

public class NumberUtils {


    public static int randomInt() {
        return randomInt(Integer.MAX_VALUE);
    }

    public static int randomInt(int bound) {
        return (int) (Math.random() * bound);
    }
}
