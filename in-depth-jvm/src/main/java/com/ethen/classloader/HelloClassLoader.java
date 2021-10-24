package com.ethen.classloader;

import com.ethen.common.User;

public class HelloClassLoader {
    public static void main(String[] args) throws ClassNotFoundException {
        final ClassLoader appClassLoader = User.class.getClassLoader();
        System.err.println("appClassLoader:" + appClassLoader);
        final ClassLoader appParent = appClassLoader.getParent();
        System.err.println("appParent" + appParent);
        final ClassLoader parent = appParent.getParent();
        System.err.println("parent" + parent);
        appClassLoader.loadClass("java.lang.String");
    }
}
