package com.ethen.spring.util;

import java.util.Objects;

public class StringUtils {

    public static boolean isEmpty(String input) {
        return input == null || input.length() == 0;
    }

    public static String firstLower(String input) {
        Objects.requireNonNull(input);
        final char[] chars = input.toCharArray();
        if (chars[0] >= 65 && chars[0] <= 90) {
            chars[0] += 32;
            return String.valueOf(chars);
        }
        return input;
    }
}
