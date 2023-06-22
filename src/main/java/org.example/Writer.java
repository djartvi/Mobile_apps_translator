package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void writeLineToFile(String file, String line) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(line);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
