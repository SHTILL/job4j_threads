package ru.job4j.pool.forkjoin;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParallelIndexSearchTest {
    @Test
    public void whenOneIndex() {
        final int collectionSize = 500;
        List<Integer> collection = IntStream.rangeClosed(0, collectionSize)
                .boxed().collect(Collectors.toList());
        Integer searchFor =  (int) (Math.random() * collectionSize);
        ParallelIndexSearch<Integer> s = new ParallelIndexSearch<>(collection.toArray(new Integer[0]), searchFor, 0, collection.size() - 1);
        int[] res = s.invoke();
        int[] expect = {searchFor};
        Assert.assertArrayEquals(expect, res);
    }

    @Test
    public void whenMultipleIndex() {
        final int collectionSize = 500;
        List<Integer> collection = IntStream.rangeClosed(0, collectionSize)
                .boxed().collect(Collectors.toList());
        int searchFor =  (int) (Math.random() * collectionSize);
        int duplicate;
        do {
            duplicate = (int) (Math.random() * collectionSize);
        } while (duplicate == searchFor);
        collection.set(duplicate, searchFor);
        ParallelIndexSearch<Integer> s = new ParallelIndexSearch<>(collection.toArray(new Integer[0]), searchFor, 0, collection.size() - 1);
        Integer[] res = Arrays.stream(s.invoke()).boxed().toArray(Integer[]::new);
        Integer[] expect = {searchFor, duplicate};
        assertTrue(Arrays.asList(res).containsAll(Arrays.asList(expect)));
    }

    @Test
    public void whenNotFound() {
        final int collectionSize = 500;
        List<Integer> collection = IntStream.rangeClosed(0, collectionSize)
                .boxed().collect(Collectors.toList());
        ParallelIndexSearch<Integer> s = new ParallelIndexSearch<>(collection.toArray(new Integer[0]), collectionSize + 1, 0, collection.size() - 1);
        Integer[] res = Arrays.stream(s.invoke()).boxed().toArray(Integer[]::new);
        assertEquals(0, res.length);
    }
}