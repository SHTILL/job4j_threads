package ru.job4j.interaction;

public class CountBarrierUsage {
    public static void main(String[] args) {
        final int total = 5;
        CountBarrier countBarrier = new CountBarrier(total);
        Thread slaveCount = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    System.out.println(Thread.currentThread().getName() + " waiting");
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " unlocked");
                },
                "CountSlave"
        );
        slaveCount.start();
        Thread masterCount = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 0; i < total; i++) {
                        System.out.println("count is: " + (i + 1));
                        countBarrier.count();
                    }
                },
                "CountMaster"
        );
        masterCount.start();
    }
}
