package replacers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static main.Constants.FROM_PATH;

public class Find {

        Scanner sc;
        File file;
        String line;

        public void find(List<String> listOfDirs, String fromFileName, String whatToFind) {
            for (String dirName : listOfDirs) {
                file = new File(FROM_PATH + dirName + "/" + fromFileName);

                if (!file.exists()) {
                    continue; // перейти в следующую директорию, если файл не найден
                }

                try {
                    sc = new Scanner(file);

                    while (sc.hasNextLine()) {
                        line = sc.nextLine();
                        if (line.contains(whatToFind)) {
                            System.out.println(dirName);
                            System.out.println(line);
                        }
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
