package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.example.Paths.*;

public class FromOneToAnother {
    Scanner sc;
    File fromFile;
    File toFile;
    String result;
    String line;
    Map<String, String> keys = new HashMap<>();

    public void replaceFromTo(List<String> listOfDirs, String fileName) throws FileNotFoundException {
        for (String dirName : listOfDirs) {
            fromFile = new File(BASE_PATH + FROM_PATH + dirName + "/" + fileName);
            toFile = new File(BASE_PATH + TO_PATH + dirName + "/" + fileName);

            try {
                sc = new Scanner(fromFile);

                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    if (line.contains("\">")) {
                        keys.put(line.split("\">")[0], line.split("\">")[1]);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }

            try {
                sc = new Scanner(toFile);
                result = BASE_PATH + MERGED_PATH + dirName + "/" + fileName;
                new File(result).getParentFile().mkdirs();

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    for (String key : keys.keySet()) {
                        if (line.contains(key)) {
                            line = key + "\">" + keys.get(key);
                            break;
                        }
                    }

                    Writer.writeLineToFile(result, line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
        }
    }
}

