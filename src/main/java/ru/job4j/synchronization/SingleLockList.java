package ru.job4j.synchronization;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private SimpleArray<T> list = new SimpleArray<>();

    synchronized public void add(T value) {
        list.add(value);
    }

    synchronized public T get(int index) {
        return list.get(index);
    }

    private SimpleArray<T> copy(SimpleArray<T> src) {
        SimpleArray<T> dst = new SimpleArray<>();
        for (T item: src) {
            dst.add(item);
        }
        return dst;
    }

    @Override
    synchronized public Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}
