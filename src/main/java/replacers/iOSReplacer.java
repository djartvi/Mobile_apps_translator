package replacers;

import files.DirsAndFiles;
import main.Settings;
import files.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class iOSReplacer {
    Scanner sc;
    File file;
    String lprojDir, line, dir;
    String[] keyValue;

    public void replaceToXml(String PATH, String FileName) {
        new File(Settings.RESULT_PATH).mkdir();
        file = new File(PATH + FileName);

        try {
            sc = new Scanner(file);
            dir = Settings.RESULT_PATH + Settings.XML_FILE;
            Writer.writeToFile(dir, "<resources>");

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                if (line.contains(" = ")) {
                    keyValue = line.split(" = ");
                    line = String.format("<string name=\"%s\">%s</string>",
                            keyValue[0].stripLeading(), keyValue[1].substring(1, keyValue[1].lastIndexOf("\"")));
                    Writer.writeToFile(dir, line);
                }
            }

            Writer.writeToFile(dir, "</resources>");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
        }

    public void replaceToStrings(String path) {
        List<String> listOfDirs = new DirsAndFiles().createListOfFiles(path);

        for (String dirName : listOfDirs) {
            file = new File(path + dirName + "/" + Settings.XML_FILE);

            try {
                lprojDir = dirName.substring(7, 9) + ".lproj/";
                dir = Settings.RESULT_PATH + lprojDir + Settings.LOCALIZABLE_FILE;
                new File(dir).getParentFile().mkdirs();
                sc = new Scanner(file);
                sc.nextLine();

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    if (line.contains("<string")) {
                        line = line
                                .replace("<string name=\"", "")
                                .replace("\">", " = \"")
                                .replace("</string>", "\";");

                        Writer.writeToFile(dir, line.trim());
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