package ru.job4j.nonbloking.cache;

public class Base {
    private final int id;
    private int version;
    private String name;

    public Base(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void increaseVersion() {
        this.version++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
