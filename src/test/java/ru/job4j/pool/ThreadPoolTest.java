package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class ThreadPoolTest {
    private static class MyTask implements Runnable {
        private String  name;
        private AtomicInteger counter;
        private int timeOut;

        public MyTask(String name, AtomicInteger counter, int timeOut) {
            this.name = name;
            this.counter = counter;
            this.timeOut = timeOut;
        }

        @Override
        public void run() {
            System.out.println(name + " Sleep for " + timeOut);
            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println(name + " completed in thread: " + Thread.currentThread());
            counter.incrementAndGet();
        }
    }

    @Test
    public void whenHaveToWork() {
        final int workNum = 4;
        ThreadPool pool = new ThreadPool();
        Runnable[] tasks = new Runnable[workNum];
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < workNum; i++) {
            tasks[i] = new MyTask("Task" + i, count, 500);
        }
        for (Runnable t : tasks) {
            pool.work(t);
        }
        while (pool.hasOngoingTasks()) {
            int i; // dummy
        }
        assertEquals(workNum, count.get());
    }

    @Test
    public void whenShutdown() {
        final int workNum = 4;
        AtomicInteger count = new AtomicInteger(0);
        ThreadPool pool = new ThreadPool();
        Runnable[] tasks = new Runnable[workNum];
        for (int i = 0; i < workNum; i++) {
            tasks[i] = new MyTask("Task" + i, count, i * 100);
        }
        for (Runnable t : tasks) {
            pool.work(t);
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        pool.shutdown();
        assertThat(count.get(), lessThan(workNum));
    }
}