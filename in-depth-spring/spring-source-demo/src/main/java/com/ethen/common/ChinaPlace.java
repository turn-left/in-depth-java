package com.ethen.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface ChinaPlace {
    PlaceLevel level() default PlaceLevel.CITY;

    enum PlaceLevel {
        PROVINCE, CITY, TOWN
    }
}
