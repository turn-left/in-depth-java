package com.ethen.spring.beans;


import com.ethen.spring.annotation.Scope.BeanScope;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BeanDefinition {
    private String name;
    private Class<?> klass;
    private BeanScope scope = BeanScope.singleton;
    private boolean isLazy;

    public boolean isSingleton() {
        return scope == BeanScope.singleton;
    }
}
