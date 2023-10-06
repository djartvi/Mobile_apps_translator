package replacers;

import files.DirsAndFiles;
import files.Paths;
import files.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Compare {
    Scanner sc;
    File firstFile, secondFile;
    String result, line;
    List<String> strings = new ArrayList<>();

    // сравнивает строки в файлах, в папке с рабочего стола
    public void compareLinesInFiles(String dirName, String firstFileName, String secondFileName) {
            firstFile = new File( Paths.BASE_PATH + dirName + "/" + firstFileName);
            secondFile = new File(Paths.BASE_PATH + dirName + "/" + secondFileName);

            try {
                sc = new Scanner(firstFile);
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    strings.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }

            try {
                sc = new Scanner(secondFile);
                result = Paths.BASE_PATH + dirName + "/result.strings";
                new DirsAndFiles().rewriteFile(result);

                Writer.writeToFile(result, "-----этих строк нет в первом файле-----");

                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    if (strings.contains(line)) {
                        strings.remove(line);
                    } else {
                        Writer.writeToFile(result, line);
                    }
                }

                if (!strings.isEmpty()) {
                    Writer.writeToFile(result, "-----этих строк нет во втором файле-----");
                    for (String string : strings) {
                        Writer.writeToFile(result, string);
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
