package com.ethen.spring.annotation;

import com.ethen.spring.beans.BeanDefinition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {
    BeanScope value() default BeanScope.prototype;


    enum BeanScope {
        prototype, singleton
    }
}
