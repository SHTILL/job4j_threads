package ru.job4j.nonbloking.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAdded() {
        Cache c = new Cache();
        Base b = new Base(1, 0, "Origin");
        assertTrue(c.add(b));
        assertThat("Origin", is(c.get(1).getName()));
    }

    @Test
    public void whenDeleted() {
        Cache c = new Cache();
        Base b = new Base(1, 0, "Origin");
        assertTrue(c.add(b));
        c.delete(b);
        assertThat(null, is(c.get(1)));
    }

    @Test
    public void whenUpdated() {
        Cache c = new Cache();
        Base b = new Base(1, 0, "Origin");
        assertTrue(c.add(b));
        Base copy1 = c.get(1);
        copy1 = copy1.setName("Copy1");
        c.update(copy1);
        Base copy2 = c.get(1);
        copy2 = copy2.setName("Copy2");
        c.update(copy2);
        assertThat("Copy2", is(c.get(1).getName()));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdatedOptimistic() {
        Cache c = new Cache();
        Base b = new Base(1, 0, "Origin");
        assertTrue(c.add(b));
        Base copy1 = c.get(1);
        Base copy2 = c.get(1);
        copy1 = copy1.setName("Copy1");
        c.update(copy1);
        copy2 = copy2.setName("Copy2");
        c.update(copy2);
    }
}