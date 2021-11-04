package com.ethen.spring.beans;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BeanDefinition {
    private Class<?> klass;
    private BeanScope scope;
    private boolean isLazy;


    public enum BeanScope {
        singleton, prototype
    }
}
