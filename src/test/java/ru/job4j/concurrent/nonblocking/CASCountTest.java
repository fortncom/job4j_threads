package ru.job4j.concurrent.nonblocking;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrementTwiceThenGet2() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(casCount::increment);
        Thread second = new Thread(casCount::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get(), Is.is(2));
    }
}