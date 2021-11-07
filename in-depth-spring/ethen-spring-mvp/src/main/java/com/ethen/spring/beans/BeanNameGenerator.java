package com.ethen.spring.beans;

import com.ethen.spring.annotation.Component;
import com.ethen.spring.util.StringUtils;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class BeanNameGenerator {

    /**
     * 推断bean的名称
     * <p>
     * fixme 假设Service实现多个接口，如何确定beanName ??
     */
    public static String gen(Class<?> beanClass) {
        final Component comp = beanClass.getAnnotation(Component.class);
        Objects.requireNonNull(comp);
        String beanName = comp.value();
        if (StringUtils.isEmpty(beanName)) {
            final Class<?>[] interfaces = beanClass.getInterfaces();
            if (interfaces.length > 0) {
                Optional<String> first = Stream.of(interfaces).map(Class::getSimpleName)
                        .filter(item -> beanClass.getSimpleName().contains(item))
                        .findFirst();
                beanName = first.map(StringUtils::firstLower).orElseGet(() -> StringUtils.firstLower(beanClass.getSimpleName()));
            } else {
                beanName = StringUtils.firstLower(beanClass.getSimpleName());
            }
        }
        return beanName;
    }
}
