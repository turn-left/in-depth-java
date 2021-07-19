package com.ethen.chap02.fkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用fork/join框架完成文件遍历
 *
 * @author ethenyang@126.com
 * @since 2021/07/14
 */
public class ListFileTask extends RecursiveAction {
    private File file;

    public ListFileTask(String path) {
        this.file = new File(path);
    }

    public ListFileTask(File file) {
        this.file = file;
    }

    static AtomicInteger counter = new AtomicInteger();

    @Override
    protected void compute() {
        final File[] files = file.listFiles();
        if (files == null) return;
        List<ListFileTask> subTasks = new ArrayList<>();

        for (File f : files) {
            if (f.isDirectory()) {
                // 继续细分任务
                subTasks.add(new ListFileTask(f));
            } else {
                if (f.getName().endsWith(".txt")) {
                    counter.incrementAndGet();
                    System.out.println(f.getAbsolutePath());
                }
            }
        }
        if (!subTasks.isEmpty()) {
            for (ListFileTask t : invokeAll(subTasks))
                t.join();
        }
    }

    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        final ForkJoinPool pool = new ForkJoinPool();
        final ListFileTask task = new ListFileTask("d:\\");
        pool.execute(task);
        task.join();
        System.err.println("end task find " + counter + " related file, cost: " + (System.currentTimeMillis() - start));
    }
}
