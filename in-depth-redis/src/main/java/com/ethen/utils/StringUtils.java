package com.ethen.utils;

import java.util.Locale;
import java.util.UUID;

public class StringUtils {
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String userKey(String userId) {
        return String.format(Locale.ENGLISH, "user:%s", userId);
    }

    public static String articleKey(String articleId) {
        return String.format(Locale.ENGLISH, "article:%s", articleId);
    }

    public static String zanKey(String articleId) {
        return String.format(Locale.ENGLISH, "zan:%s", articleId);
    }
}
