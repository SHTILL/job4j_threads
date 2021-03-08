package ru.job4j.pool.forkjoin;

import org.junit.Test;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

public class ParallelMergeSortTest {
    @Test
    public void whenSort() {
        final int size = 100000;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * 8000);
        }
        int[] sorted = ParallelMergeSort.sort(origin);
        for (int i = 0; i < (size - 1); i++) {
            assertThat(sorted[i],  lessThanOrEqualTo(sorted[i + 1]));
        }
    }
}