package com.ethen.classloader;

import com.ethen.common.User;

public class SelfClassLoader extends ClassLoader {


    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.err.println("User classLoader:" + User.class.getClassLoader());
        final SelfClassLoader selfClassLoader = new SelfClassLoader();
        final Class<?> aClass = selfClassLoader.loadClass("com.ethen.common.User");
        final Object obj = aClass.newInstance();
        System.err.println("obj classLoader:" + obj.getClass().getClassLoader());
        System.err.println(obj instanceof User);


    }
}
