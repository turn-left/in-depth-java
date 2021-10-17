package com.ethen.agent;

import java.lang.instrument.Instrumentation;

/**
 * java agent 执行
 * <p>
 * fixme 待分析MANIFEST.MF文件定义
 */
public class MyAgent {
    public static void premain(String args, Instrumentation inst) {
        System.out.println("============= MyAgent 执行中 ===== args:" + args + " ============");
    }
}
