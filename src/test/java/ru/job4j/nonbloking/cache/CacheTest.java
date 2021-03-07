package ru.job4j.nonbloking.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAddedSucceed() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertTrue(c.add(b));
        assertThat("Origin", is(c.get(1).getName()));
    }

    @Test
    public void whenAddedFailed() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertTrue(c.add(b));
        assertFalse(c.add(b));
    }

    @Test
    public void whenDeleted() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertTrue(c.add(b));
        c.delete(b);
        assertThat(null, is(c.get(1)));
    }

    @Test
    public void whenUpdateSucceed() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertTrue(c.add(b));
        b.setName("New value");
        assertTrue(c.update(b));
        assertThat("New value", is(c.get(1).getName()));
    }

    @Test
    public void whenUpdateNotAdded() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertFalse(c.update(b));
    }

    @Test
    public void whenUpdateDeleted() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertTrue(c.add(b));
        assertTrue(c.update(b));
        assertTrue(c.delete(b));
        assertFalse(c.update(b));
    }

    @Test
    public void whenUpdateLastWins() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertTrue(c.add(b));
        Base copy1 = c.get(1);
        copy1.setName("Copy1");
        Base copy2 = c.get(1);
        copy2.setName("Copy2");
        assertTrue(c.update(copy1));
        assertTrue(c.update(copy2));
        assertThat("Copy2", is(c.get(1).getName()));
    }

    @Test(expected = OptimisticException.class)
    public void whenOptimisticException() {
        Cache c = new Cache();
        Base b = new Base(1, "Origin");
        assertTrue(c.add(b));
        Base copy1 = new Base(1, "Copy1");
        assertTrue(c.update(copy1));
        Base copy2 = new Base(1, "Copy2");
        c.update(copy2);
    }
}