package ru.job4j.synchronization;

import java.io.*;
import java.util.stream.Collectors;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized ParseFile setFile(File f) {
        return new ParseFile(f);
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            return r.lines().collect(Collectors.joining());
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        char[] read = new char[(int) file.length()];
        int data;
        int count = 0;
        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = is.read()) > 0) {
                if (data < 0x80) {
                    read[count] = (char) data;
                    count++;
                }
            }
        }
        return new String(read, 0, count);
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(file))) {
            w.write(content);
        }
    }
}
