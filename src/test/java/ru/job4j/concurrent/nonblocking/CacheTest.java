package ru.job4j.concurrent.nonblocking;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenUpdateThenVersion1() {
        Cache map = new Cache();
        Base baseOld = new Base(1, 0);
        Base baseNew = new Base(1, 0);
        baseNew.setName("User1");
        map.add(baseOld);
        map.update(baseNew);
        Base rsl = map.get(baseOld.getId());
        assertThat(rsl.getVersion(), Is.is(1));
    }

    @Test
    public void whenDeleteThenFindAllReturnListEmpty() {
        Cache map = new Cache();
        Base base = new Base(1, 0);
        map.add(base);
        map.delete(base);
        assertTrue(map.findAll().isEmpty());
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateNotEqualVersionThenOptimisticEx() {
        Cache map = new Cache();
        Base baseOld = new Base(1, 0);
        Base baseNew = new Base(1, 3);
        map.add(baseOld);
        map.update(baseNew);
    }
}