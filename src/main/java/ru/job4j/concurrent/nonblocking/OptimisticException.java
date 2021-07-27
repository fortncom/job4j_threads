package ru.job4j.concurrent.nonblocking;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
