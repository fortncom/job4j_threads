package ru.job4j.concurrent.nonblocking;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer expected;
        Integer newVal;
        do {
            expected = count.get();
            newVal = expected + 1;
        } while (!count.compareAndSet(expected, newVal));
    }

    public int get() {
        return count.get();
    }
}
