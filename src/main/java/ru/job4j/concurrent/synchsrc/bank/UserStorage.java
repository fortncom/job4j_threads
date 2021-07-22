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
        User rsl = store.putIfAbsent(user.getId(), user);
        return rsl != null && rsl.equals(user);
    }

    @Override
    public synchronized boolean update(User user) {
        User rsl = store.replace(user.getId(), user);
        return rsl != null && rsl.equals(user);
    }

    @Override
    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
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
