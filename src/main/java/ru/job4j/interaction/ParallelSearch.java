package ru.job4j.interaction;

public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        System.out.println(queue.poll());
                    }
                }
        );
        consumer.start();
        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }

        );

        producer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        consumer.stop();
    }
}
