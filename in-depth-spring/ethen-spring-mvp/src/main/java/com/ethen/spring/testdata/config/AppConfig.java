package com.ethen.spring.testdata.config;

import com.ethen.spring.annotation.Component;
import com.ethen.spring.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.ethen.spring"})
@Component
public class AppConfig {
}
