package ru.job4j.interaction;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void whenMultiThread() {
        SimpleBlockingQueue<Integer> q = new SimpleBlockingQueue<>();

        Thread producer = new Thread(() -> {
            q.offer(3);
            try {
                Thread.currentThread().join(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            q.offer(17);
        });

        Thread consumer = new Thread(() -> {
            int actual = q.poll();
            assertThat(actual, is(3));
            actual = q.poll();
            assertThat(actual, is(17));
        });

        consumer.start();
        try {
            consumer.join(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        producer.start();

        try {
            consumer.join();
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}