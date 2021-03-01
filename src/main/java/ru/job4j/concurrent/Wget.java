package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Wget implements Runnable {
    private final static char[] PROGRESS = {'\\', '|', '/', '-'};

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        System.out.println("Start downloading");
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
                long timeStartLoading = System.currentTimeMillis();
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                long tookTime = System.currentTimeMillis() - timeStartLoading;
                if (tookTime < speed) {
                    try {
                        Thread.sleep(speed - tookTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        } catch (IOException e) {
            System.out.print("Downloading failed");
            return;
        }
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
