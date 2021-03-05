package ru.job4j.pool;

public enum SingletonEnum {
    INSTANCE;
    SingletonEnum() {
        System.out.println("Enum");
    }

    static void init() {
        System.out.println("Init enum");
    }
}