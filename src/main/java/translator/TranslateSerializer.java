package translator;

import files.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static files.Paths.*;

public class TranslateSerializer {
    Scanner sc;
    File file;
    String lprojDir, line, dir, key, value;
    List<String> keys = new ArrayList<>();
    List<String> splitValues = new ArrayList<>();

    public TranslateSerializer parseXml() {
        file = new File(VALUES_PATH + XML_FILE);
        StringBuilder toTranslate = new StringBuilder();

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
        return this;
    }

    public TranslateSerializer parseIOS() {
        file = new File(LOCALIZABLE_PATH + LOCALIZABLE_FILE);
        StringBuilder toTranslate = new StringBuilder();
        String[] keyValue;

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

        return this;
    }

    public void buildXmlFile(String PATH, String stringBody) {
        new File(PATH).getParentFile().mkdirs();

        Writer.writeToFile(PATH, "<resources>");

        sc = new Scanner(stringBody);
        int i = 0;
        while (sc.hasNextLine()) {
            line = String.format("<string name=\"%s\">%s</string>", keys.get(i), sc.nextLine());
            Writer.writeToFile(PATH, line.trim());
            i++;
        }
        Writer.writeToFile(PATH, "</resources>");
    }

    public void buildIOSFile(String PATH, String stringBody) {
        new File(PATH).getParentFile().mkdirs();

        sc = new Scanner(stringBody);
        int i = 0;
        while (sc.hasNextLine()) {
            line = String.format("%s = \"%s\";", keys.get(i), sc.nextLine());
            Writer.writeToFile(PATH, line.trim());
            i++;
        }
    }
}