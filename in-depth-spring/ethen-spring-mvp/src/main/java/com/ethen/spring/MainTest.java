package com.ethen.spring;


import com.ethen.spring.context.EthenApplicationContext;
import com.ethen.spring.testdata.bean.RegisterService;
import com.ethen.spring.testdata.config.AppConfig;
import com.ethen.spring.testdata.bean.UserService;

public class MainTest {
    public static void main(String[] args) {
        EthenApplicationContext applicationContext = new EthenApplicationContext(AppConfig.class);
        RegisterService registerService = applicationContext.getBean("registerService");
        registerService.register();
    }
}
