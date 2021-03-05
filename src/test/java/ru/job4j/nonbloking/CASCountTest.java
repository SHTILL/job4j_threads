package ru.job4j.nonbloking;

import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void whenIncrement() {
        CASCount c = new CASCount();
        Thread t1 = new Thread(c::increment);
        Thread t2 = new Thread(c::increment);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(2, c.get());
    }

}