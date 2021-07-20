package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;
    private String outPath = ".tmp";

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    @Override
    public void run() {
        try {
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(outPath)) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    int count = 0;
                    long startTime = System.currentTimeMillis();
                    char[] chars = {'-', '\\', '|', '/'};
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        count++;
                        if (count == chars.length) {
                            count = 0;
                        }
                        System.out.print("\r loading: " + "process[" + chars[count] + "]");
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        Thread.sleep(100);
                    }
                long endTime = System.currentTimeMillis();
                long rslTime = endTime - startTime;
                if (speed > rslTime) {
                    Thread.sleep(speed - rslTime);
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
        if (args.length != 2) {
            throw new IllegalArgumentException("Insufficient number of input parameters Usage java PATH TIME.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Wget wget = new Wget(url, speed);
        wget.setOutPath("src/main/resources/tmp.file");
        Thread thread = new Thread(wget);
        thread.start();
        thread.join();
    }
}
