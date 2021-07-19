package com.ethen.common;

import java.util.Random;

/**
 * @author ethenyang@126.com
 * @since 2021/07/14
 */
public class ArrayCreator {
    public static int[] getArray(int len) {
        Random random = new Random();
        final int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = random.nextInt(len * 5);
        }
        return arr;
    }
}
