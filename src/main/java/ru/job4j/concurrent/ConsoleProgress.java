package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int count = 0;
        char[] chars = {'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            if (count == chars.length) {
                count = 0;
            }
            System.out.print("\r load: " + "process[" + chars[count++] + "]");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
