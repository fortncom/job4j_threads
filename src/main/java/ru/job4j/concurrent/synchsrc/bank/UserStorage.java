package ru.job4j.concurrent.synchsrc.bank;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
class UserStorage implements Storage {

    @GuardedBy("this")
    private final Map<Integer, User> store = new HashMap<>();
    @Override
    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!store.containsKey(user.getId())) {
            store.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (store.containsKey(user.getId())) {
            store.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (store.containsKey(user.getId())) {
            rsl = store.remove(user.getId(), user);
        }
        return rsl;
    }

    @Override
    public synchronized Optional<User> findById(int id) {
        Optional<User> rsl = Optional.empty();
        if (store.containsKey(id)) {
            rsl = Optional.of(store.get(id));
        }
        return rsl;
    }
}
