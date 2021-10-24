package com.ethen.agent.javassist.chap01;

import javassist.ClassPool;
import javassist.LoaderClassPath;

import java.util.List;

/**
 * 第一个javassist程序
 */
public class JavassistHelloWorld {
    public static void main(String[] args) {
        ClassPool classPool = new ClassPool(true);
        classPool.insertClassPath(new LoaderClassPath(JavassistHelloWorld.class.getClassLoader()));
    }

}
