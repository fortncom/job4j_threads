package ru.job4j.concurrent.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;
    private final T[] array;
    private final T item;
    private final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, T item, int from, int to) {
        this.array = array;
        this.item = item;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= THRESHOLD) {
           return processing();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, item,  from, mid);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, item,  mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return left > right ? left : right;
    }

    private int processing() {
        int result = -1;
        for (int i = from; i < to; i++) {
            if (array[i].equals(item)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static <T> int searchIndex(T[] array, T item) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        return forkJoinPool.invoke(new ParallelSearchIndex<>(array, item, 0, array.length - 1));
    }
}