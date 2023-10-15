package replacers;

import files.DirsAndFiles;
import files.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static main.Constants.*;

public class Replacer {
    Scanner sc;
    File file;
    String line, resultDir, resultFile;

    public void replace(String path, String... replacedValues) {
        List<String> listOfFiles = new DirsAndFiles().createListOfFiles(path);
        resultDir = path.substring(0, path.length() - 1) + REPLACED_DIR_POSTFIX + SLASH;

        for (String filePath : listOfFiles) {
            file = new File(path + SLASH + filePath);

            try {
                sc = new Scanner(file);
                resultFile = resultDir + filePath;
                new File(resultFile).getParentFile().mkdirs();

                while (sc.hasNextLine()) {
                    line = replaceParts(sc.nextLine(), replacedValues);

                    if (line.contains("%@")) {
                        System.out.println(line);
                    }

                    Writer.writeToFile(resultFile, line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
        }
    }

    // Передаём мап. Ключ - элемент, который хотим изменить. Значение - элемент, на который меняем
    private String replaceParts(String line, String... replacedValues) {
        for (int i = 0; i < replacedValues.length; i = i + 2) {
            line = line.replace(replacedValues[i], replacedValues[i + 1]);
        }
        return line;
    }
}
