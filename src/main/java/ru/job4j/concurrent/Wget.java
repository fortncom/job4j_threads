package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/tmp.file")) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    int count = 0;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        long startTime = System.currentTimeMillis();
                        count++;
                        String s = count % 2 == 0 ? " " :  "wait";
                        System.out.print("\r loading: " + s);
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        long endTime = System.currentTimeMillis();
                        long rslTime = endTime - startTime;
                        if (speed > rslTime) {
                            Thread.sleep(speed - rslTime);
                        }
                    }
                System.out.print("\rLoading is complete.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
