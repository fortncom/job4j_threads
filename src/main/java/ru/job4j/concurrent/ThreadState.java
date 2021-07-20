package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(Thread.currentThread().getName());
        Thread second = new Thread(Thread.currentThread().getName());
        System.out.println("First state: ".concat(first.getState().toString()));
        first.start();
        System.out.println("Second state: ".concat(second.getState().toString()));
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("First state: ".concat(first.getState().toString()));
            System.out.println("Second state: ".concat(second.getState().toString()));
        }
        System.out.println("First state: ".concat(second.getState().toString()));
        System.out.println("Second state: ".concat(second.getState().toString()));
        System.out.println("Работа всех нитей завершена");
    }
}
