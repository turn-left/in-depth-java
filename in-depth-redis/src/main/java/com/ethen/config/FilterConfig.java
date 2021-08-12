package com.ethen.config;

import com.ethen.usecase.CurrentLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CurrentLimitFilter> register(JedisConnectionFactory factory) {
        FilterRegistrationBean<CurrentLimitFilter> register = new FilterRegistrationBean<>();
        register.setFilter(new CurrentLimitFilter(factory, 20, 100));
        register.addUrlPatterns("/*");
        register.setName("CurrentLimitFilter");
        register.setOrder(1);
        return register;
    }
}
