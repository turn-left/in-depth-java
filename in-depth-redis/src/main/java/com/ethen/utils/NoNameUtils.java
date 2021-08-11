package com.ethen.utils;

import lombok.SneakyThrows;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NoNameUtils {


    @SneakyThrows
    public Map<String, String> object2map(Object obj) {
        Objects.requireNonNull(obj);
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> result = new HashMap<>();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = field.get(obj);
            result.put(name, String.valueOf(value));
        }
        return result;
    }
}
