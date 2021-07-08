package com.ethen.chap04;

import com.ethen.common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * description: OutOfMemoryError分析方法
 * <p>
 * JVM参数:
 * -Xms30m
 * -xmx30m
 * -XX:+HeapDumpOnOutOfMemoryError 当内存一处时打印线程dump信息
 * -XX:+PrintGCDetails
 * {@linkplain} MAT分析工具
 * <p>
 * 内存溢出:程序在申请内存时，没有足够的内存空间
 * 内存泄漏原因分析：
 * 1.长生命周期对象持有段生命周期对象引用
 * 2.连接未释放
 * 3.变量作用域不合理
 * 4.内部类持有外部类
 * 5.hash值改变
 *
 * @author ethenyang@126.com
 * @since 2021/07/04
 */
public class OOMAnalyze {
    public static void main(String[] args) {
        List<byte[]> bigObjects = new ArrayList<>();
        while (true) {
            byte[] bigObj = new byte[Constants._1M];
            bigObjects.add(bigObj);
        }
    }
}
