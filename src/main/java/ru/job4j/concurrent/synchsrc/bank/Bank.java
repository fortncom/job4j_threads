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
        Optional<User> userFromOpt = storage.findById(from);
        Optional<User> userToOpt = storage.findById(to);
        if (userFromOpt.isPresent() && userToOpt.isPresent()) {
            User userFrom = userFromOpt.get();
            if (userFrom.getAmount() >= amount) {
                userFrom.setAmount(userFrom.getAmount() - amount);
                User userTo = userToOpt.get();
                userTo.setAmount(userTo.getAmount() + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
