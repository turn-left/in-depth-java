package com.ethen.chap02.contools;


import com.ethen.common.ArrayCreator;
import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class UseFuture {

    @AllArgsConstructor
    static class ComputeTask implements Callable<Long> {
        private int[] source;

        @Override
        public Long call() {
            System.err.println("ComputeTask start ...");
            long result = 1;
            if (source == null) throw new IllegalArgumentException();
            for (int k = 0; k < source.length; k++) {
                System.out.println("ComputeTask 正在执行->k=" + k);
                result += source[k];
            }
            System.err.println("ComputeTask end !!!");
            return result;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ComputeTask task = new ComputeTask(ArrayCreator.getArray(100));
        FutureTask<Long> futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        int factor = (int) (Math.random() * 100);
        if (factor > 50) {
            System.err.println("完成任务-" + futureTask.get());
        } else {
            System.err.println("取消任务 factor=" + factor);
            futureTask.cancel(true);
        }


    }

}
