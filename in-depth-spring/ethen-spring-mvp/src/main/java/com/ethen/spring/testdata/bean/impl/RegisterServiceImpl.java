package com.ethen.spring.testdata.bean.impl;

import com.ethen.spring.annotation.Autowired;
import com.ethen.spring.annotation.Component;
import com.ethen.spring.testdata.bean.RegisterService;
import com.ethen.spring.testdata.bean.UserService;

@Component
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserService userService;

    @Override
    public void register() {
        System.err.println("RegisterService do register() ...");
    }

    public void test() {
        System.err.println("userService:" + userService);
    }
}
