package com.ethen.chap05.exam.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TurnLeftBlockQueue<E> implements BlockingQueue<E> {
    private final Lock lock = new ReentrantLock();
    private final Condition putCond = lock.newCondition();
    private final Condition takeCond = lock.newCondition();
    private final int capacity;
    private int modCount; // 当前size
    private Node<E> tail;
    private Node<E> head;

    /**
     * 队列节点类
     *
     * @param <E>
     */
    @Data
    @AllArgsConstructor
    static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        public boolean hasNext() {
            return next != null;
        }
    }

    public TurnLeftBlockQueue(int capacity) {
        this.capacity = capacity;
        this.modCount = 0;
    }

    /**
     * 向队尾添加元素，满了抛出异常
     */
    @Override
    public boolean add(E e) {
        lock.lock();
        try {
            if (modCount >= capacity)
                throw new IllegalStateException("queue full");
            this.addLast(e);
            return true;
        } finally {
            lock.unlock();
        }
    }


    /**
     * 向队尾添加元素，满了阻塞
     */
    @Override
    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (this.modCount >= this.capacity) {
                putCond.await();
            }
            this.addLast(e);
            this.takeCond.signal();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 向队尾添加元素,满了返回false
     */
    @Override
    public boolean offer(E e) {
        lock.lock();
        try {
            if (modCount >= capacity) {
                return false;
            }
            this.addLast(e);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }
    }

    // 向队列末端添加元素
    private void addLast(E e) {
        Node<E> newNode = new Node<>(e, null, null);
        Node<E> t = this.tail;
        // 队列不为空
        if (modCount > 0 && this.tail != null) {
            newNode.prev = t;
            t.next = this.tail = newNode;
        } else {
            this.head = this.tail = newNode;
        }
        modCount++;
    }


    @Override
    public E remove() {
        lock.lock();
        try {
            if (this.modCount == 0 && this.tail == null)
                throw new IllegalStateException("queue empty");
            return this.removeFirst();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    private E removeFirst() {
        if (head == null) return null;
        Node<E> h = head;
        Node<E> n = h.next;
        if (this.modCount > 0) {
            this.head = n;
            if (n != null) n.prev = null; // help GC
//            h.setValue(null);// help GC
            modCount--;
        }
        return h.getValue();
    }

    @Override
    public E poll() {
        lock.lock();
        try {
            return this.removeFirst();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (modCount <= 0 || this.tail == null) putCond.await();
            E e = this.removeFirst();
            this.putCond.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return modCount == 0 ? null : head.getValue();
    }


    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return capacity - modCount;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
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
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }

    private E findNextVal(Node<E> node) {
        if (node == null || node.next == null)
            return null;
        return node.getNext().getValue();
    }


    @Override
    public String toString() {
        if (modCount == 0) return "";
        StringBuilder sb = new StringBuilder("[");
        for (Node<E> n = head; n.hasNext(); n = n.next) {
            if (n != head) sb.append(",");
            if (n.getValue() != null) sb.append(n.getValue());
        }
        return sb.append("]").toString();
    }
}
