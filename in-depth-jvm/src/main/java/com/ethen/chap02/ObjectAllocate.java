package com.ethen.chap02;

/**
 * description:
 *
 * @author ethenyang@126.com
 * @since 2021/07/03
 */
public class ObjectAllocate {

    public static class User {
        public int id = 0;
        public String name = "";
    }

    public static void alloc() {
        final User user = new User(); // 对象在堆内存上分配
        user.id = 12345;
        user.name = "steve.jobs";
    }

    public static void main(String[] args) {
        alloc();
    }
}
