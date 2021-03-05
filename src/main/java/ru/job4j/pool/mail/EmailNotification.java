package ru.job4j.pool.mail;

import java.util.concurrent.*;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void send(String subject, String body, String email) {
        System.out.println("Sending email ...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("Email is sent");
    }

    public Future<?> emailTo(User user) {
        String subject = new StringBuilder()
                .append("Notification ")
                .append(user.getUsername())
                .append(" to email ")
                .append(user.getEmail()).toString();
        String body = new StringBuilder()
                .append("Add a new event to ")
                .append(user.getUsername()).toString();
        return pool.submit(() -> send(subject, body, user.getEmail()));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            int i; //dummy
        }
    }

    public static void main(String[] args) {
        EmailNotification service = new EmailNotification();
        User u1 = new User("John", "john@mail.com");
        Future<?> f = service.emailTo(u1);
        while (!f.isDone()) {
            int i; // dummy
        }
        service.close();
        System.out.println("Exit");
    }
}
