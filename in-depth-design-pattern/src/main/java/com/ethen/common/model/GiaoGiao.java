package com.ethen.common.model;

import lombok.Data;

@Data
public class GiaoGiao {
    public GiaoGiao() {
        System.out.println("构造Giao哥");
    }
    /**
     * 该方法不能被子类覆盖,Cglib无法代理final修饰的方法
     */
    final public String sing(String name) {
        System.out.println("Giao哥：开始唱"+name);
        return "giao哥唱完了" ;
    }
    public void dance() {
        System.out.println("Giao哥：边唱边giao");
    }
}