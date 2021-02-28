package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private static char[] PROGRESS = { '\\', '|', '/', '-' };

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + PROGRESS[i%PROGRESS.length]);
            i++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args)  {
        Thread t1 = new Thread(new ConsoleProgress());
        t1.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        System.out.println( "");
        System.out.print("Finish");
    }
}
