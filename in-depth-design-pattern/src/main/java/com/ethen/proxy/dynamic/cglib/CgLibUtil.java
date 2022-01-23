package com.ethen.proxy.dynamic.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Field;
import java.util.Properties;

public class CgLibUtil {
    /**
     * 保存CGLib生成的代理类
     *
     * @param dir 存放目录
     */
    public static void enableSaveProxyFiles(String dir) {
        try {
            Field field = System.class.getDeclaredField("props");
            field.setAccessible(true);
            Properties props = (Properties) field.get(null);
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, dir);
            props.put("net.sf.cglib.core.DebuggingClassWriter.traceEnabled", "true");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
