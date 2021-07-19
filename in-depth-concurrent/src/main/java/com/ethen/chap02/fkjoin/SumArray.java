package com.ethen.chap02.fkjoin;

import com.ethen.common.ArrayCreator;

import java.awt.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author ethenyang@126.com
 * @since 2021/07/14
 */
public class SumArray {
    private static final int ARRAY_LEN = 500000000;
    public static final int THRESHOLD = ARRAY_LEN / 20;

    static class SumTask extends RecursiveTask<Integer> {
        private int[] input;
        private int fromIndex;
        private int toIndex;

        public SumTask(int[] input, int fromIndex, int toIndex) {
            this.input = input;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if (toIndex - fromIndex < THRESHOLD) {
                // do business
                int sum = 0;
                for (int i = fromIndex; i < toIndex; i++) {
                    sum += input[i];
                }
                return sum;
            } else {
                // fork task 二分法
                int mid = (toIndex + fromIndex) / 2;
                final SumTask left = new SumTask(input, fromIndex, mid);
                final SumTask right = new SumTask(input, mid + 1, toIndex);
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int[] input = ArrayCreator.getArray(ARRAY_LEN);
        final ForkJoinPool pool = new ForkJoinPool();
        final SumTask sumTask = new SumTask(input, 0, ARRAY_LEN - 1);
        Integer result = pool.invoke(sumTask);
        System.err.println("end task result:" + result + ",cost:" + (System.currentTimeMillis() - start));
    }
}
