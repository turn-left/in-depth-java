package com.ethen.spring.testdata.aspect;

import com.ethen.spring.annotation.Aspect;
import com.ethen.spring.annotation.Component;

@Component
@Aspect
public class AuditAspect {
    public void before() {
        System.err.println("AuditAspect before() ...");
    }

    public void after() {
        System.err.println("AuditAspect after() ...");
    }
}
