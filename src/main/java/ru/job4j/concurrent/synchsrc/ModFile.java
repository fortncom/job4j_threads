package ru.job4j.concurrent.synchsrc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ModFile {

    private final File file;

    public ModFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i++) {
                out.write(content.charAt(i));
            }
        }
    }
}
