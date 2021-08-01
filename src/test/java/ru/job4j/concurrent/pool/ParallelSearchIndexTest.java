package ru.job4j.concurrent.pool;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class ParallelSearchIndexTest {

    @Test
    public void whenArrayContains4Index2Then2() {
        Integer[] array = new Integer[] {9, 8, 4, 7, 1, 2};
        Integer index = ParallelSearchIndex.searchIndex(array, 4);
        assertThat(index, Is.is(2));
    }
}