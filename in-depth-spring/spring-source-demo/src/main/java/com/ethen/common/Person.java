package com.ethen.common;

import lombok.Data;

@Data
public class Person {
    private int age;
    private String name;

    public String sayHello() {
        return "Hello,I'm " + name + ".Welcome to spring source!";
    }
}
