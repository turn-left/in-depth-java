package com.ethen.common;

import java.util.HashMap;

public class Demo001 {
    public static void main(String[] args) {
        final HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("张三", 123);
        System.err.println(objectObjectHashMap);
    }
}
