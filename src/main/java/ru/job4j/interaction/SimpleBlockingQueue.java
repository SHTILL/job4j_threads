package ru.job4j.interaction;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) throws InterruptedException {
        while (!queue.offer(value)) {
            this.wait();
        }
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
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
