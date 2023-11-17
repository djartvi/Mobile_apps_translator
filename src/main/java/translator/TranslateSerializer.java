package translator;

import files.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static main.Constants.*;

public class TranslateSerializer {
    private Scanner sc;
    private File file;
    private String line, key, value, resultPath;
    private String[] keyValue;
    private final List<String> keys = new ArrayList<>();
    private final List<String> splitValues = new ArrayList<>();
    private StringBuilder toTranslate = new StringBuilder();

    public TranslateSerializer parse(String platform, String path) {
        switch (platform) {
            case "android":
                parseXml(path);
                break;
            case "ios":
                parseIOS(path);
                break;
        }

        return this;
    }

    public TranslateSerializer build(String platform, String path, String language, String stringBody) {
        switch (platform) {
            case "android":
                resultPath = path + "values_" + language + SLASH + XML_FILE;
                buildXmlFile(resultPath, stringBody);
                break;
            case "ios":
                // В iOS нужно 2 локали для норвежского языка
                if (language.equals("no")) {
                    for (String norwayLang : new String[]{"nb", "nn"}) {
                        resultPath = path + norwayLang + ".lproj" + SLASH + LOCALIZABLE_FILE;
                        buildIOSFile(resultPath, stringBody);
                    }
                } else {
                    resultPath = path + language + ".lproj" + SLASH + LOCALIZABLE_FILE;
                    buildIOSFile(resultPath, stringBody);
                }

                break;
        }

        return this;
    }

    private void parseXml(String path) {
        file = new File(path + XML_FILE);

        try {
            sc = new Scanner(file);

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                if (line.contains("<string")) {
                    key = line.substring(line.indexOf("=\"") + 2, line.indexOf("\">"));
                    value = line.substring(line.indexOf("\">") + 2, line.indexOf("</"));

                    keys.add(key);
                    toTranslate.append(value).append("\n");

                    if (keys.size() % 50 == 0) {
                        splitValues.add(toTranslate.toString());
                        toTranslate = new StringBuilder();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert sc != null;
            sc.close();
        }

        if (keys.size() % 50 != 0) {
            splitValues.add(toTranslate.toString());
        }
    }

    private void parseIOS(String path) {
        file = new File(path + LOCALIZABLE_FILE);

        try {
            sc = new Scanner(file);

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                if (line.contains(" = ")) {
                    keyValue = line.split(" = ");
                    keys.add(keyValue[0]);

                    toTranslate.append(keyValue[1]
                            .replace("\"", "")
                            .replace(";", ""))
                            .append("\n");

                    // отправляем на перевод по 50 строк в запросе, иначе есть риск, что вернётся ошибка
                    if (keys.size() % 50 == 0) {
                        splitValues.add(toTranslate.toString());
                        toTranslate = new StringBuilder();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert sc != null;
            sc.close();
        }

        // если строк меньше, чем 50, то этот остаток тоже отправляем на перевод
        if (keys.size() % 50 != 0) {
            splitValues.add(toTranslate.toString());
        }
    }

    private void buildXmlFile(String path, String stringBody) {
        new File(path).getParentFile().mkdirs();

        Writer.writeToFile(path, "<resources>");

        sc = new Scanner(stringBody);
        int i = 0;
        int j = 0;
        int k = 0;

        while (sc.hasNextLine()) {
            line = String.format("<string name=\"%s\">%s</string>", keys.get(i), sc.nextLine());

            // Блок для проверки качества перевода. Часто слетают переводы и параметризация
            if (line.contains("%@")) {
                System.out.println(line);
                j++ ;
            } else if (line.contains("\\n")) {
                System.out.println(line);
                k++;
            }

            Writer.writeToFile(path, line.trim());
            i++;
        }

        Writer.writeToFile(path, "</resources>");

        System.err.printf("Параметров \"%%\": %d. Переносов строки: %d.\n", j, k);

    }

    private void buildIOSFile(String path, String stringBody) {
        new File(path).getParentFile().mkdirs();

        sc = new Scanner(stringBody);
        int i = 0;
        int j = 0;
        int k = 0;
        while (sc.hasNextLine()) {
            line = String.format("%s = \"%s\";", keys.get(i), sc.nextLine());

            // Блок для проверки качества перевода. Часто слетают переводы и параметризация
            if (line.contains("%@")) {
                System.out.println(line);
                j++ ;
            } else if (line.contains("\\n")) {
                System.out.println(line);
                k++;
            }

            Writer.writeToFile(path, line.trim());
            i++;
        }

        System.err.printf("Параметров \"%%\": %d. Переносов строки: %d.\n", j, k);
    }

    public List<String> getSplitValues() {
        return splitValues;
    }
}