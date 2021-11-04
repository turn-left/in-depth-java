package com.ethen.spring;

import com.ethen.spring.context.SimpleApplicationContext;
import com.ethen.spring.config.AppConfig;

public class MainTest {
    public static void main(String[] args) {
        SimpleApplicationContext applicationContext = new SimpleApplicationContext(AppConfig.class);

    }
}
