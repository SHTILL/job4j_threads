package ru.job4j.synchronization;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayIterator<E> implements Iterator<E> {
    private final int expectedModCount;
    private final int containerSize;
    private Object[] container;
    private SimpleArray<E> array;
    private int position;


    public SimpleArrayIterator(int expectedModCount, Object[] container, int containerSize, SimpleArray<E> array) {
        this.expectedModCount = expectedModCount;
        this.container = container;
        this.array = array;
        this.containerSize = containerSize;
    }

    @Override
    public boolean hasNext() {
        return position < containerSize;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (expectedModCount != array.getModCount()) {
            throw new ConcurrentModificationException();
        }
        return (E) container[position++];
    }
}
