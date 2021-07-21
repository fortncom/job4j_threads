package ru.job4j.concurrent.synchsrc.bank;

import java.util.Optional;

interface Storage {

    boolean add(User user);
    boolean update(User user);
    boolean delete(User user);
    Optional<User> findById(int id);
}
