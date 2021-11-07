package com.ethen.spring.testdata.bean.impl;

import com.ethen.spring.annotation.Autowired;
import com.ethen.spring.annotation.Component;
import com.ethen.spring.beans.InitializingBean;
import com.ethen.spring.testdata.bean.RegisterService;
import com.ethen.spring.testdata.bean.UserService;

@Component
public class RegisterServiceImpl implements RegisterService, InitializingBean {

    @Autowired
    private UserService userService;

    @Override
    public void register() {
        System.err.println("RegisterService do register() ...");
        System.err.println("userService:" + userService);
    }

    @Override
    public void afterPropertiesSet() {
        System.err.println("RegisterServiceImpl do initializing afterPropertiesSet()");
    }
}
