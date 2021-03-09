package ru.job4j.pool;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum
                    && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    private static int calcSumRow(int[] row) {
        int res = 0;
        for (int i: row) {
            res += i;
        }
        return res;
    }

    private static int calcSumCol(int[][] matrix, int idx) {
        int res = 0;
        for (int[] row : matrix) {
            res += row[idx];
        }
        return res;
    }

    private static Sums calcSums(int[][] matrix, int idx) {
        return new Sums(calcSumRow(matrix[idx]), calcSumCol(matrix, idx));
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] s = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            s[i] = calcSums(matrix, i);
        }
        return s;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Sums>> sumsFutures = new ArrayList<>(matrix.length);
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int j = i;
            sumsFutures.add(CompletableFuture.supplyAsync(() -> calcSums(matrix, j)));
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = sumsFutures.get(i).get();
        }
        return sums;
    }
}
