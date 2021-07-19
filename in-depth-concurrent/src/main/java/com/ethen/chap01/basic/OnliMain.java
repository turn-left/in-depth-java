package com.ethen.chap01.basic;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * description:java天生是多线程的
 * <p>
 * [6] Monitor Ctrl-Break //监控 Ctrl-Break 中断信号的
 * [5] Attach Listener //内存 dump，线程 dump，类信息统计，获取系统属性等
 * [4] Signal Dispatcher // 分发处理发送给 JVM 信号的线程
 * [3] Finalizer // 调用对象 finalize 方法的线程
 * [2] Reference Handler//清除 Reference 的线程
 * [1] main //main 线程，用户程序入口
 *
 * @author ethenyang@126.com
 * @since 2021/07/10
 */
public class OnliMain {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        for (ThreadInfo threadInfo : threadInfos) {
            System.err.println("threadName-" + threadInfo.getThreadName() + ",threadId- " + threadInfo.getThreadId() + ",state-" + threadInfo.getThreadState());
        }
    }
}
