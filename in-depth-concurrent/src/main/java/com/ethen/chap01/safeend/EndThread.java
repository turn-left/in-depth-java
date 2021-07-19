package com.ethen.chap01.safeend;

import java.util.concurrent.TimeUnit;

/**
 * 终止线程方式
 *
 * @author ethenyang@126.com
 * @since 2021/07/10
 */
public class EndThread extends Thread {
    private int count;

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
//        while (true) {
//        while (!isInterrupted()) {
            System.err.println("EndThread is running count:" + count++ + ",flag:" + isInterrupted());
        }
        System.err.println("EndThread is about to end flag:" + isInterrupted());
    }

    public static void main(String[] args) {
        EndThread endThread = new EndThread();
        endThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 在main线程中调用endThread线程的中断方法，发出中断信号
        endThread.interrupt();
    }
}
