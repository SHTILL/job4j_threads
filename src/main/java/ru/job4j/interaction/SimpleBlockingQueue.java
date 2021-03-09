package ru.job4j.interaction;

import net.jcip.annotations.GuardedBy;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    synchronized public void offer(T value) throws InterruptedException {
        while (!queue.offer(value)) {
            this.wait();
        }
        this.notifyAll();
    }

    synchronized public T poll() throws InterruptedException {
        T item;
        while ((item = queue.poll()) == null) {
            this.wait();
        }
        this.notifyAll();
        return item;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
