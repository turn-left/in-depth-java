package com.ethen.project.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TaskResult<R> {
    private ResultType resultType;
    private R resultData;
    private String message;

    public final static TaskResult<?> FAIL = new TaskResult<>(ResultType.FAIL, null, "task failed!");
    public final static TaskResult<?> EXCEPTION = new TaskResult<>(ResultType.EXCEPTION, null, "internal error!");

    public boolean isSuccess() {
        return resultType == ResultType.SUCCESS;
    }

    enum ResultType {
        SUCCESS, // 成功
        FAIL, // 执行完毕，结果失败
        EXCEPTION // 执行异常
    }
}
