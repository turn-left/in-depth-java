package com.ethen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages = {"com.ethen"})
public class LockApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LockApp.class, args);
        System.err.println(Arrays.stream(context.getBeanDefinitionNames()).collect(Collectors.toList()));
    }
}
