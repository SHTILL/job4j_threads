package ru.job4j.synchronization;

import java.util.Iterator;
import java.util.Objects;

public class SimpleArray<T> implements Iterable<T> {
    private static final int EXTEND_BY = 10;
    private Object[] container = {};
    private int size;
    private int position;
    private int modCount;

    public T get(int index) {
        Objects.checkIndex(index, position);
        return (T) container[index];
    }

    public boolean add(T model) {
        if (position >= size) {
            size += EXTEND_BY;
            Object[] extended = new Object[size];
            System.arraycopy(container, 0, extended, 0, container.length);
            container = extended;
        }
        container[position] = model;
        position++;
        modCount++;
        return true;
    }

    public boolean contains(T value) {
        for (int i0 = 0; i0 < position; i0++) {
            if (Objects.equals(container[i0], value)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return position;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleArrayIterator<>(modCount, container, position, this);
    }

    public int getModCount() {
        return modCount;
    }
}
