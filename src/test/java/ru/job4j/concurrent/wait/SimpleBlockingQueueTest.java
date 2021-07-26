package ru.job4j.concurrent.wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenProducerParseStringThenConsumerGetIt() {
        String input = "Test for blocking queue";
        final CopyOnWriteArrayList<Character> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Character> queue = new SimpleBlockingQueue<>(input.length());
        Thread producer = new Thread(
                () -> {
                        input.chars().mapToObj(value -> (char) value).forEach(character -> {
                            try {
                                queue.offer(character);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        try {
            producer.join();
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Character> expected = input.chars().mapToObj(
                value -> (char) value).collect(Collectors.toList());
        assertThat(buffer, is(expected));
    }
}