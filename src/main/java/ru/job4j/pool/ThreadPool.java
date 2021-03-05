package ru.job4j.pool;

import ru.job4j.interaction.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
    private final AtomicInteger busyThreads = new AtomicInteger(0);
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread t = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                Runnable task = tasks.poll();
                                busyThreads.incrementAndGet();
                                task.run();
                                busyThreads.decrementAndGet();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            threads.add(t);
            t.start();
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
        while (hasOngoingTasks()) {
            int i; //dummy
        }
    }

    public boolean hasOngoingTasks() {
        if (!tasks.isEmpty()) {
            return true;
        }
        final long spin = 1000;
        for (long i = 0; i < spin; i++) {
            if (busyThreads.get() != 0) {
                return true;
            }
        }
        return false;
    }
}
