package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        int timeMsPerKb = 1000 / speed;

        System.out.println("Start downloading");
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            do {
                long timeStartLoading = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, 1024);
                fileOutputStream.write(dataBuffer, 0, Math.max(bytesRead, 0));
                long tookTime = System.currentTimeMillis() - timeStartLoading;
                if (tookTime < timeMsPerKb) {
                    try {
                        Thread.sleep(timeMsPerKb - tookTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } while (bytesRead != -1);
        } catch (IOException e) {
            System.out.print("Downloading failed");
            return;
        }
        System.out.println();
        System.out.println("Finish downloading");
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
