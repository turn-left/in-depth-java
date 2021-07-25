package com.ethen.chap05.exam.queue;

import com.ethen.common.NumberUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestBlockingQue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> bq = new TurnLeftBlockQueue<>(10);
        try {
            for (int k = 0; k < 100; k++) {
//                bq.add(NumberUtils.randomInt());
                            boolean offer = bq.offer(NumberUtils.randomInt());
                            if (!offer) break;
                //            bq.put(NumberUtils.randomInt());
                System.err.println("成功添加第" + k + "个元素");
            }
        } finally {
            System.err.println(bq);
        }
    }
}
