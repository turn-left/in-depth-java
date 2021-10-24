package com.ethen.agent.javassist.chap02;


import java.lang.reflect.Method;

public class Demo01 implements com.ethen.agent.common.UserDao {

    public static void main(String[] args) {
        final Demo01 demo01 = new Demo01();
        demo01.method123();
    }

    public void method123() {
        System.err.println("========================建立数据库连接======================");
        System.err.println("========================准备sql语句========================");
        final Class<?>[] interfaces = getClass().getInterfaces();
        try {
            final Method method = interfaces[0].getDeclaredMethod("selectAll");
            final com.ethen.agent.annotation.Select annotation = method.getAnnotation(com.ethen.agent.annotation.Select.class);
            final String sql = annotation.value();
            System.err.println("========================准备sql语句========================");
            System.err.println("========================" + sql + "========================");
            System.err.println("========================执行完毕释放连接========================");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
