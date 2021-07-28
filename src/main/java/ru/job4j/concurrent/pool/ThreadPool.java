package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

    public void init() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Performer performer = new Performer(tasks);
            threads.add(performer);
            performer.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    static class Performer extends Thread {

        private final SimpleBlockingQueue<Runnable> tasks;

        public Performer(SimpleBlockingQueue<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (tasks.isEmpty()) {
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                try {
                    Runnable job = tasks.poll();
                    job.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
