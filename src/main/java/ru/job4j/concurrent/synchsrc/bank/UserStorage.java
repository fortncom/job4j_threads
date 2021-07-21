package ru.job4j.concurrent.synchsrc.bank;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
class UserStorage implements Storage {

    @GuardedBy("this")
    private final List<User> store = new ArrayList<>();

    @Override
    public synchronized boolean add(User user) {
        return !store.contains(user) && store.add(user);
    }

    @Override
    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (store.contains(user)) {
           store.set(store.indexOf(user), user);
           rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (store.contains(user)) {
            rsl = store.remove(user);
        }
        return rsl;
    }

    @Override
    public synchronized Optional<User> findById(int id) {
        Optional<User> rsl = Optional.empty();
        for (User user : store) {
            if (user.getId() == id) {
                rsl = Optional.of(user);
                break;
            }
        }
        return rsl;
    }
}
