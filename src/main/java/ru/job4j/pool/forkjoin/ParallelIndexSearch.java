package ru.job4j.pool.forkjoin;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<int[]> {
    private static final int COMPUTE_SEQ_SIZE = 10;
    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    private int[] findIndex() {
        int[] indexes = new int[COMPUTE_SEQ_SIZE];
        int size = 0;
        for (int i = from; i < to; i++) {
            if (array[i].equals(value)) {
                indexes[size] = i;
                size++;
            }
        }
        return Arrays.copyOf(indexes, size);
    }

    @Override
    protected int[] compute() {
        if ((from - to) < COMPUTE_SEQ_SIZE) {
            return findIndex();
        }

        int mid = (from + to) / 2;
        ParallelIndexSearch<T> left = new ParallelIndexSearch<>(this.array, value, from, mid);
        ParallelIndexSearch<T> right = new ParallelIndexSearch<>(this.array, value, mid + 1, to);

        left.fork();
        right.fork();

        ForkJoinTask.invokeAll(left, right);

        int[] leftRes = left.join();
        int[] rightRes =  right.join();
        return ArrayUtils.addAll(leftRes, rightRes);
    }
}
