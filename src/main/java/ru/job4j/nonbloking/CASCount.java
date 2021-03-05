package ru.job4j.nonbloking;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        count.updateAndGet((i) -> i + 1);
    }

    public int get() {
        return count.get();
    }
}
