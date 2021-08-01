package ru.job4j.concurrent.pool;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertArrayEquals;

public class RolColSumTest {

    int[][] input = new int[][]{
            {1, 2},
            {4, 5}
    };

    RolColSum.Sums[] expected = new RolColSum.Sums[2];

    @Before
    public void initExpected() {
        RolColSum.Sums first = new RolColSum.Sums();
        first.setRowSum(3);
        first.setColSum(5);
        expected[0] = first;
        RolColSum.Sums second = new RolColSum.Sums();
        second.setRowSum(9);
        second.setColSum(7);
        expected[1] = second;
    }

    @Test
    public void sum() {
        RolColSum.Sums[] rsl = RolColSum.sum(input);
        assertArrayEquals(expected, rsl);
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] rsl = RolColSum.asyncSum(input);
        assertArrayEquals(expected, rsl);
    }

}