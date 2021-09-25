package com.ethen.aop;

import com.ethen.util.TimeUtil;

/**
 * 核心逻辑
 */
public interface Performance {
    default void perform() {
        System.err.println("@@Performance perform()==");
        TimeUtil.spend(998L);
    }
}
