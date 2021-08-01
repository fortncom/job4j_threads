package ru.job4j.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndicesToList<T> extends RecursiveTask<List<Integer>> {

    private static final int THRESHOLD = 10;
    private final T[] array;
    private final T item;
    private final int from;
    private final int to;

    public ParallelSearchIndicesToList(T[] array, T item, int from, int to) {
        this.array = array;
        this.item = item;
        this.from = from;
        this.to = to;
    }

    @Override
    protected List<Integer> compute() {
        if (to - from + 1 <= THRESHOLD) {
           return processing();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndicesToList<T> leftSearch = new ParallelSearchIndicesToList<>(array, item,  from, mid);
        ParallelSearchIndicesToList<T> rightSearch = new ParallelSearchIndicesToList<>(array, item,  mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        List<Integer> rslList = new ArrayList<>();
        rslList.addAll(leftSearch.join());
        rslList.addAll(rightSearch.join());
        return rslList;
    }

    private List<Integer> processing() {
        List<Integer> rsl = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            if (array[i].equals(item)) {
                rsl.add(i);
            }
        }
        return rsl;
    }

    public static <T> List<Integer> searchIndex(T[] array, T item) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        return forkJoinPool.invoke(new ParallelSearchIndicesToList<>(array, item, 0, array.length - 1));
    }
}
