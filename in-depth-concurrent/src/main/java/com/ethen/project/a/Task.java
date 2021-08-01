package com.ethen.project.a;

/**
 * 任务接口 -> 要求框架调用者实现
 */
public interface Task<R, T> {
    /**
     * 任务执行逻辑
     *
     * @param data 入参
     * @return 返回
     */
    TaskResult<R> execute(T data);
}
