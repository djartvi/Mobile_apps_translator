package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.example.Paths.*;

public class iOSReplacer {
    Scanner sc;
    File file;

    public void replaceToXml() {
        new File(RESULT_PATH).mkdir();
        file = new File(BASE_PATH + LOCALIZABLE_DIR + LOCALIZABLE_FILE);
        String dir;

        try {
            sc = new Scanner(file);
            dir = RESULT_PATH + XML_FILE;
            Writer.writeLineToFile(dir, "<resources>");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String replaced;

                if (line.contains(" = ")) {
                    String[] keyValue = line.split(" = ");
                    replaced = String.format("<string name=\"%s\">%s</string>",
                            keyValue[0].stripLeading(), keyValue[1].substring(1, keyValue[1].lastIndexOf("\"")));
                    Writer.writeLineToFile(dir, replaced);
                }
            }

            Writer.writeLineToFile(dir, "</resources>");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
        }

    public void replaceToStrings(List<String> listOfDirs) {
        new File(RESULT_PATH).mkdir();

        for (String dirName : listOfDirs) {
            file = new File(BASE_PATH + MERGED_PATH + dirName + "/" + "Localizable.xml");

            try {
                sc = new Scanner(file);
                String dir = RESULT_PATH + dirName + "/" + LOCALIZABLE_FILE;
                new File(dir).getParentFile().mkdirs();
                String line = sc.nextLine();
                String replaced;

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    if (line.contains("<string")) {
                        replaced = line
                                .replace("<string name=\"", "")
                                .replace("\">", " = \"")
                                .replace("</string>", "\";")
                                .replace("55", "%d")
                                .replace("55", "%d")
                                .replace(" \\\\ ", "\\n")
                                .replace("\\\\ ", "\\n")
                                .replace(" \\\\", "\\n")
                                .replace("\\\\", "\\n")
                                .replace("[", "\\'")
                                .replace("]", "\\'")
                                .replace("\u200B", "");

                        if (replaced.contains("@")) {
                            System.out.println(dirName);
                            System.out.println(replaced);
                        }

                        Writer.writeLineToFile(dir, replaced);
                    }
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
        }
    }
}