package ru.job4j.interaction;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (this) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (this) {
            while (count != total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
