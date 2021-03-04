package ru.job4j.synchronization;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {
    @Test
    public void whenTransfer() {
        UserStorage store = new UserStorage();

        User from = new User(1, 100);
        assertTrue(store.add(from));
        User to = new User(2, 200);
        assertTrue(store.add(to));

        store.transfer(1, 2, 50);
        assertEquals(50, from.getAmount());
        assertEquals(250, to.getAmount());
    }

    @Test
    public void whenDeletePositive() {
        UserStorage store = new UserStorage();

        User u = new User(1, 100);
        assertTrue(store.add(u));
        assertTrue(store.delete(u));
    }

    @Test
    public void whenDeleteNegative() {
        UserStorage store = new UserStorage();

        User u1 = new User(1, 100);
        assertTrue(store.add(u1));
        User u2 = new User(2, 100);
        assertFalse(store.delete(u2));
    }

    @Test
    public void whenUpdatePositive() {
        UserStorage store = new UserStorage();

        User u1 = new User(1, 100);
        assertTrue(store.add(u1));
        assertTrue(store.update(u1));
    }

    @Test
    public void whenUpdateNegative() {
        UserStorage store = new UserStorage();

        User u1 = new User(1, 100);
        assertTrue(store.add(u1));
        User u2 = new User(2, 100);
        assertFalse(store.update(u2));
    }
}