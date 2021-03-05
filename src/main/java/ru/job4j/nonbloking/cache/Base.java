package ru.job4j.nonbloking.cache;

public class Base {
    private final int id;
    private final int version;
    private final String name;

    public Base(int id, int version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public Base setVersion(int version) {
        return new Base(this.id, version, this.name);
    }

    public String getName() {
        return name;
    }

    public Base setName(String name) {
        return new Base(this.id, this.version, name);
    }
}
