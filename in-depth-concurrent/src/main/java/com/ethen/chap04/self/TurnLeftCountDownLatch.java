package com.ethen.chap04.self;

import lombok.AllArgsConstructor;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 根据AQS自制并发工具CountDownLatch
 * <p>
 * 当指定的工作线程执行扣减，并扣减为零时，阻塞在资源上的线程被欢迎继续执行
 *
 * @see java.util.concurrent.CountDownLatch
 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer
 */
public class TurnLeftCountDownLatch implements CountDown {
    private Sync sync;

    public TurnLeftCountDownLatch(int counter) {
        this.sync = new Sync(counter);
    }

    /**
     * 工作线程执行扣减
     */
    @Override
    public void countDown() {
        this.sync.releaseShared(1);
    }

    /**
     * count没有扣减完毕，线程阻塞
     */
    @Override
    public void await() {
        this.sync.acquireShared(0);
    }

    /**
     * 当前剩余扣减总数
     */
    @Override
    public long getCount() {
        return this.sync.getCount();
    }

    static class Sync extends AbstractQueuedSynchronizer {
        public Sync(int counter) {
            this.setState(counter);
        }

        /**
         * 返回值<0 => 入队自旋
         * <p>
         * {@link AbstractQueuedSynchronizer#acquireShared}
         */
        @Override
        protected int tryAcquireShared(int arg) {
            return this.getState() > 0 ? -1 : 1;
        }

        /**
         * return true => 当前节点被移出队列、唤醒，且向next节点传播
         * {@link AbstractQueuedSynchronizer#releaseShared}
         */
        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int c = this.getState();
                if (c <= 0)
                    return false;
                int remaining = c - arg;
                if (compareAndSetState(c, remaining)) {
                    return remaining <= 0;
                }
            }
        }

        public long getCount() {
            return getState();
        }
    }
}


