package com.ethen.loadbean;

import com.ethen.common.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlLoadBeanTest {
    public static void main(String[] args) {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-load.xml");
        final Person bean = context.getBean(Person.class);
        System.err.println(bean.sayHello());
    }
}
