package com.ethen.chap02.yang;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 使用list实现队列
 * FIFO-->先进先出
 *
 * @param <E>
 */
public class ListQueue<E> implements Queue<E> {
    private final List<E> list = new ArrayList<>();

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return Queue.super.removeIf(filter);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Queue.super.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return Queue.super.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return Queue.super.parallelStream();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        if (isEmpty()) throw new NoSuchElementException();
        return list.remove(0);
    }

    @Override
    public E poll() {
        return isEmpty() ? null : list.remove(0);
    }

    @Override
    public E element() {
        if (isEmpty()) throw new NoSuchElementException();
        return list.get(0);
    }

    @Override
    public E peek() {
        return list.get(0);
    }
}
