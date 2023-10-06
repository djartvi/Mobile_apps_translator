package files;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void writeToFile(String filePath, String line) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(line);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
