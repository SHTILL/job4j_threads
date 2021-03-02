package ru.job4j.synchronization;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenTransfer() {
        UserStorage store = new UserStorage();

        User from = new User(1, 100);
        store.add(from);
        User to = new User(2, 200);
        store.add(to);

        store.transfer(1, 2, 50);
        assertEquals(50, from.getAmount());
        assertEquals(250, to.getAmount());
    }
}