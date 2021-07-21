package ru.job4j.concurrent.synchsrc.bank;

import java.util.Optional;

class Bank implements Transfer {

    private final UserStorage storage;

    public Bank(UserStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean transfer(int from, int to, int amount) {
        boolean rsl = false;
        Optional<User> user1 = storage.findById(from);
        Optional<User> user2 = storage.findById(to);
        if (user1.isPresent() && user2.isPresent()) {
            User userFrom = user1.get();
            if (userFrom.getAmount() >= amount) {
                userFrom.setAmount(userFrom.getAmount() - amount);
                User userTo = user2.get();
                userTo.setAmount(userTo.getAmount() + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
