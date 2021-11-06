package com.ethen.spring;

import com.ethen.spring.aspect.AuditAspect;
import com.ethen.spring.context.EthenApplicationContext;
import com.ethen.spring.config.AppConfig;

public class MainTest {
    public static void main(String[] args) {
        EthenApplicationContext applicationContext = new EthenApplicationContext(AppConfig.class);
        AuditAspect auditAspect = applicationContext.getBean("auditAspect");
        auditAspect.before();

    }
}
