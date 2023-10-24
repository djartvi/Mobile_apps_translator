package replacers;

import files.DirsAndFiles;
import files.Writer;
import translator.DaysOfWeek;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static main.Constants.*;

public class Replacer {
    Scanner sc;
    File file;
    String line, resultDir, resultFile, currentLanguage, buildedLine;

    public void replace(String path, String... replacedValues) {
        List<String> listOfFiles = new DirsAndFiles().createListOfFiles(path);

        // Задаём имя для новой папки с результатами
        resultDir = path.substring(0, path.length() - 1) + REPLACED_DIR_POSTFIX;

        for (String filePath : listOfFiles) {
            file = new File(filePath);

            try {
                sc = new Scanner(file);
                resultFile = filePath.replace(path, resultDir);
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

    // Отдельный метод для дней недели. Корректно переводится сокращения, только когда указана последовательность "Пн Вт Ср ..."
    public void replaceDaysOfWeek(String path, String platform) {
        List<String> listOfFiles = new DirsAndFiles().createListOfFiles(path);
        Map<String, Map<Byte, String>> daysOfWeekTranslates = new DaysOfWeek().getLanguageMap();
        String[] weekDaysKeys = {"plan_week_mon", "plan_week_tue", "plan_week_wed",
                "plan_week_thu", "plan_week_fri", "plan_week_sat", "plan_week_sun"};

        // Задаём имя для новой папки с результатами
        resultDir = path.substring(0, path.length() - 1) + REPLACED_DIR_POSTFIX;

        for (String filePath : listOfFiles) {
            file = new File(filePath);

            currentLanguage = getLanguageFromPath(platform, filePath);

            System.out.println(currentLanguage);
            try {
                sc = new Scanner(file);
                resultFile = filePath.replace(path, resultDir);
                new File(resultFile).getParentFile().mkdirs();

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    for (byte i = 0; i <weekDaysKeys.length; i++){
                        if (line.contains(weekDaysKeys[i])) {
                            line = buildFromKey(platform, weekDaysKeys[i],
                                    daysOfWeekTranslates.get(currentLanguage).get((byte)(i + 1)));
                        }
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

    private String buildFromKey(String platform, String key, String value) {
        switch (platform) {
            case "ios":
                buildedLine = String.format("%s = \"%s\";", key, value);
                break;
            case "android":
                buildedLine = String.format("<string name=\"%s\">%s</string>", key, value);
                break;
        }

        return buildedLine;
    }

    private String getLanguageFromPath(String platform, String path) {
        switch (platform) {
            case "ios":
                currentLanguage = path.substring(path.indexOf(".lproj") - 2,  path.indexOf(".lproj"));
                break;
            case "android":
                currentLanguage = path.substring(path.lastIndexOf("values_") + 7,  path.lastIndexOf("values_") + 9);
                break;
        }

        return currentLanguage;
    }
}
