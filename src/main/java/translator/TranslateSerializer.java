package translator;

import files.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static main.Constants.*;

public class TranslateSerializer {
    Scanner sc;
    File file;
    String lprojDir, line, dir, key, value, resultPath;
    String[] keyValue;
    List<String> keys = new ArrayList<>();
    List<String> splitValues = new ArrayList<>();
    StringBuilder toTranslate = new StringBuilder();

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
                resultPath = path + language + ".lproj" + SLASH + LOCALIZABLE_FILE;
                buildIOSFile(resultPath, stringBody);
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

    private void buildXmlFile(String path, String stringBody) {
        new File(path).getParentFile().mkdirs();

        Writer.writeToFile(path, "<resources>");

        sc = new Scanner(stringBody);
        int i = 0;
        while (sc.hasNextLine()) {
            line = String.format("<string name=\"%s\">%s</string>", keys.get(i), sc.nextLine());
            Writer.writeToFile(path, line.trim());
            i++;
        }
        Writer.writeToFile(path, "</resources>");
    }

    private void buildIOSFile(String path, String stringBody) {
        new File(path).getParentFile().mkdirs();

        sc = new Scanner(stringBody);
        int i = 0;
        while (sc.hasNextLine()) {
            line = String.format("%s = \"%s\";", keys.get(i), sc.nextLine());
            Writer.writeToFile(path, line.trim());
            i++;
        }
    }
}