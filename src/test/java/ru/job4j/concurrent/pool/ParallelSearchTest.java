package ru.job4j.concurrent.pool;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenArrayContains4ThreeTimeThenGetListOf3() {
        Integer[] array = new Integer[] {9, 8, 4, 7, 1, 2, 4, 8, 2, 4};
        List<Integer> list = ParallelSearch.searchIndex(array, 4);
        List<Integer> expected = List.of(2, 6, 9);
        assertTrue(list.containsAll(expected));
    }
}