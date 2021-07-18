package ru.job4j.concurrent.commonsrc;

public class Count {

    private int value;

    public synchronized void increment() {
        value++;
    }

    public synchronized int get() {
        return value;
    }
}
