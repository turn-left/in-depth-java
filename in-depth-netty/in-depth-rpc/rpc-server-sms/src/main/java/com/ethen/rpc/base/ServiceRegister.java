package com.ethen.rpc.base;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务本地注册
 */
@Component
public class ServiceRegister {
    private static final Map<String, Class<?>> SERVICE_CACHE = new ConcurrentHashMap<>();

    /**
     * 注册服务
     */
    public void registerService(String serviceName, Class<?> impl) {
        Objects.requireNonNull(serviceName);
        Objects.requireNonNull(impl);
        SERVICE_CACHE.put(serviceName, impl);
    }

    /**
     * 获取服务
     */
    public Class<?> getLocalService(String serviceName) {
        Objects.requireNonNull(serviceName);
        return SERVICE_CACHE.get(serviceName);
    }
}
