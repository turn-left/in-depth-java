package com.ethen.test;

import com.ethen.common.Person;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public class SpringProxyTest {

    public static void main(String[] args) {
        Person person = new Person();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(person);
        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.err.println("=============before============");
                Object result = invocation.proceed();
                System.err.println("=============after============");
                System.err.println("result==>" + result);
                return result;
            }
        });

        Person proxy = (Person) proxyFactory.getProxy();
        proxy.sayHello();
        proxy.dance();

    }
}
