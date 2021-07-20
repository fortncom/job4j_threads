package ru.job4j.concurrent.synchsrc;

import java.io.*;
import java.util.function.Predicate;


public class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> predicate) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = in.read()) > 0) {
                if (predicate.test((char) data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}


