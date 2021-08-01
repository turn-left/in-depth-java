package com.ethen.project.a;

import lombok.Getter;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对提交任务上下文综合信息描述
 * 任务注册信息保存
 * 结果计数
 * 任务上下文
 */
@Getter
public class JobInfo<R, T> {
    private String jobName;
    private int jobSize;
    private Task<R, T> task;
    // 阻塞队列存放结果集
    private final LinkedBlockingDeque<TaskResult<R>> resultQueue;
    private final AtomicInteger successCounter; // 成功数
    private final AtomicInteger completeCounter; // 已完成数

    public JobInfo(String jobName, int jobSize, Task<R, T> task) {
        this.jobName = jobName;
        this.jobSize = jobSize;
        this.task = task;
        this.resultQueue = new LinkedBlockingDeque<>();
        successCounter = new AtomicInteger();
        completeCounter = new AtomicInteger();
    }

    public void putResult(TaskResult<R> result) {
        resultQueue.addLast(result);
    }


}
