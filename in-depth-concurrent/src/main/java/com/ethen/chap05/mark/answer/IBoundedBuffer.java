package com.ethen.chap05.mark.answer;

public interface IBoundedBuffer<T> {

    void put(T x) throws InterruptedException;
    T take() throws InterruptedException;

}
