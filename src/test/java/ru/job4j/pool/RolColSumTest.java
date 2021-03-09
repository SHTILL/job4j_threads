package ru.job4j.pool;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {
    @Test
    public void whenSerial() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] res = RolColSum.sum(matrix);
        RolColSum.Sums[] expect = {new RolColSum.Sums(6, 12),
                                    new RolColSum.Sums(15, 15),
                                    new RolColSum.Sums(24, 18) };
        assertThat(res, is(expect));
    }

    @Test
    public void whenAsync() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] res = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] expect = {new RolColSum.Sums(6, 12),
                                    new RolColSum.Sums(15, 15),
                                    new RolColSum.Sums(24, 18) };
        assertThat(res, is(expect));
    }
}