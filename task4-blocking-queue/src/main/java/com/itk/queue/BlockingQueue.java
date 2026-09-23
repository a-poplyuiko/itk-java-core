package com.itk.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class BlockingQueue<T> {

    private final Deque<T> queue = new ArrayDeque<>();
    private final int capacity;

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.addLast(item);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.removeFirst();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }
}
