package ru.job4j.concurrent.synchsrc.bank;

interface Transfer {
    boolean transfer(int from, int to, int amount);
}
