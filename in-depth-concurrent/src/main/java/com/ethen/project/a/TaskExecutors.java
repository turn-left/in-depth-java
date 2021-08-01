package com.ethen.project.a;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 任务执行框架
 * 1.任务注册
 * 2.任务提交
 * 3.任务执行
 * 4.结果统计保存
 * 5.结果查询
 * 6.缓存过期清理
 */
public class TaskExecutors {
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    // 执行线程池
    private final ExecutorService pool;
    // 任务注册表
    private static final Map<String, JobInfo<?, ?>> registerMap = new ConcurrentHashMap<>();

    private TaskExecutors() {
        pool = new ThreadPoolExecutor(
                CORE_SIZE, CORE_SIZE * 2,
                20, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000));
    }

    // 任务框架实例采用单例
    static class TaskExecutorHolder {
        private static TaskExecutors instance = new TaskExecutors();
    }

    public static TaskExecutors fixedTaskExecutor() {
        return TaskExecutorHolder.instance;
    }

    static class ExecutorTask<R, T> implements Runnable {
        private JobInfo<R, T> jobInfo;
        private T data;

        @Override
        public void run() {
            Task<R, T> task = this.jobInfo.getTask();
            if (task == null) return;
            TaskResult taskRes = null;
            try {
                taskRes = task.execute(data);
                if (taskRes.getResultType() == null) {
                    taskRes = TaskResult.FAIL;
                } else if (taskRes.isSuccess()) {
                    jobInfo.getSuccessCounter().incrementAndGet();
                }
            } catch (Exception e) {
                System.err.println(task + " exception !!!");
            } finally {
                jobInfo.getCompleteCounter().incrementAndGet();
                jobInfo.putResult(taskRes);
            }


        }
    }


    void registerJob(String jobName, Task<?, ?> task, int jobSize) {
        JobInfo<?, ?> jobInfo = new JobInfo<>(jobName, jobSize, task);
        registerMap.putIfAbsent(jobName, jobInfo);
    }

    void submit() {
    }

    JobInfo getJobInfo(String jobName) {
        return null;
    }

    void handleExpireData() {
    }

}
