package ru.job4j.concurrent.wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {


    private class ProducerThread<T>  extends Thread {

        private final SimpleBlockingQueue<T> queue;
        List<T> list;

        private ProducerThread(final SimpleBlockingQueue<T> queue, List<T> list) {
            this.queue = queue;
            this.list = list;
        }

        @Override
        public void run() {
            for (T t : list) {
                try {
                    queue.offer(t);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private class ConsumerThread<T>  extends Thread {

        private final SimpleBlockingQueue<T> queue;
        public List<T> rsl = new ArrayList<>();

        private ConsumerThread(final SimpleBlockingQueue<T> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                try {
                    rsl.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Test
    public void whenProducerThread123ThenConsumerThread123() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> input = new ArrayList<>(List.of(1, 2, 3));
        Thread first = new ProducerThread<>(queue, input);
        ConsumerThread<Integer> second = new ConsumerThread<>(queue);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(second.rsl, is(input));
    }
}