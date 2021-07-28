package ru.job4j.concurrent.pool;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void when2workedThen4() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        pool.init();
        StringBuilder builder = new StringBuilder();
        pool.work(() -> {
            for (int i = 0; i < 2; i++) {
                builder.append((char) ('a' + i));
            }
        });
        pool.work(() -> {
            for (int i = 0; i < 2; i++) {
                builder.append((char) ('z' - i));
            }
        });
        Thread.sleep(500);
        pool.shutdown();
        assertThat(builder.length(), Is.is(4));
    }

}