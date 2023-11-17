package replacers;

import files.DirsAndFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Find {

        Scanner sc;
        File file;
        String line;

    public void find(String path, String valueToFind) {
        List<String> listOfFiles = new DirsAndFiles().createListOfFiles(path);

        for (String filePath : listOfFiles) {
            file = new File(filePath);

            try {
                sc = new Scanner(file);

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    if (line.contains(valueToFind)) {
                        System.out.println(filePath);
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
