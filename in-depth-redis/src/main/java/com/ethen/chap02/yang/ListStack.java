package com.ethen.chap02.yang;

import java.util.LinkedList;

/**
 * 使用list实现栈
 * LIFO--后进先出
 */
public class ListStack<T> {
    private final LinkedList<T> list = new LinkedList<>();

    // 入栈 push
    public void push(T item) {
        list.addFirst(item);
    }

    // 出栈 pop
    public T pop() {
        return list.removeFirst();
    }

    // peek
    public T peek() {
        return list.getFirst();
    }

    // empty
    public boolean empty() {
        return list.isEmpty();
    }
}
