package ru.job4j.concurrent;

public class Switcher {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    while (true) {
                        System.out.println("Thread A");
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        System.out.println("Thread B");
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
