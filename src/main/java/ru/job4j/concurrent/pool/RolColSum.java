package ru.job4j.concurrent.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
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

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = new Sums();
            for (int j = 0; j < matrix[i].length; j++) {
                rsl[i].rowSum += matrix[i][j];
            }
            for (int j = 0; j < matrix.length; j++) {
                rsl[i].colSum += matrix[j][i];
            }
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> tasks = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            tasks.put(i, getTask(matrix, i));
        }
        for (Integer key : tasks.keySet()) {
            rsl[key] = tasks.get(key).get();
        }
        return rsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int line) {
        Sums sums = new Sums();
        return CompletableFuture.supplyAsync(() -> {
            for (int j = 0; j < data[line].length; j++) {
                sums.rowSum += data[line][j];
            }
            return sums;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            for (int j = 0; j < data.length; j++) {
                sums.colSum += data[j][line];
            }
            return sums;
        }), (sums1, sums2) -> sums);
    }

}
