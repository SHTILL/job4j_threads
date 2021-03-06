package ru.job4j.nonbloking;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer ref;
        Integer tmp;
        do {
            ref = count.get();
            tmp = (ref + 1);
        } while (!count.compareAndSet(ref, tmp));
    }

    public int get() {
        return count.get();
    }
}
