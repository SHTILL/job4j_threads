package ru.job4j.concurrent;

public class MasterSlaveBarrier {
    private final Thread slave;
    private final Thread master;
    private final Thread barrier;

    private final Object masterLock = new Object();
    private final Object slaveLock = new Object();
    private final Object masterBarrier = new Object();
    private final Object slaveBarrier = new Object();

    public class Barrier extends Thread {
        private final Object lock;
        private final Object barrier;
        private final String name;

        public Barrier(Object lock, Object barrier, String name) {
            this.lock = lock;
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(name);
                synchronized (barrier) {
                    barrier.notify();
                }
            }
        }
    }

    public MasterSlaveBarrier() {
        slave = new Barrier(masterLock, slaveBarrier, "Slave");
        master = new Barrier(slaveLock, masterBarrier, "Master");
        barrier = new Thread(
                () -> {
                    try {
                        Thread.sleep(500);
                        unlock(slaveLock);
                        while (true) {
                            tryBarrier(masterBarrier);
                            unlock(masterLock);
                            tryBarrier(slaveBarrier);
                            unlock(slaveLock);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
    }

    public void run() {
        barrier.start();
        master.start();
        slave.start();
        try {
            barrier.join();
            master.join();
            slave.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void tryBarrier(Object barrier) throws InterruptedException {
        synchronized (barrier) {
            barrier.wait();
        }
    }

    public void unlock(Object lock) {
        synchronized (lock) {
            lock.notify();
        }
    }

    public static void main(String[] args) {
        new MasterSlaveBarrier().run();
    }
}
