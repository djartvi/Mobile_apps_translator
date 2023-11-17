package replacers;

import files.DirsAndFiles;
import files.Writer;
import main.Links;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static main.Constants.*;

public class FromOneToAnother {
    Scanner sc;
    File fromFile, toFile;
    String result, line;
    Map<String, String> keys = new HashMap<>();

    public void replaceFromTo(List<String> listOfDirs, String fileName) throws FileNotFoundException {
        for (String dirName : listOfDirs) {
            fromFile = new File(FROM_PATH + dirName + "/" + fileName);
            toFile = new File(TO_PATH + dirName + "/" + fileName);

            try {
                sc = new Scanner(fromFile);
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    if (line.contains("\">")) {
                        keys.put(line.split("\">")[0], line.split("\">")[1]);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
            try {
                sc = new Scanner(toFile);
                result = MERGED_PATH + dirName + "/" + fileName;
                new File(result).getParentFile().mkdirs();

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    for (String key : keys.keySet()) {
                        if (line.contains(key)) {
                            line = key + "\">" + keys.get(key);
                            break;
                        }
                    }
                    Writer.writeToFile(result, line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
        }
    }

    public void addFromOneDirsToAnother(String fromPath, String fromFileName, String toPath, String toFileName) {
        List<String> listOfDirs = new DirsAndFiles().createListOfFiles(fromPath);

        for (String dirName : listOfDirs) {
            fromFile = new File(FROM_PATH + dirName + "/" + fromFileName);
//            toFile = new File(TO_PATH + dirName + "/" + toFileName);

            try {
                sc = new Scanner(fromFile);

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    Writer.writeToFile(TO_PATH + dirName + "/" + toFileName, line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                assert sc != null;
                sc.close();
            }
        }
    }

    public void replaceFromFileToFile(List<String> FromListOfDirs, List<String> ToListOfFiles) throws FileNotFoundException {
        new DirsAndFiles().createDir(MERGED_PATH);

        for (String dirName : FromListOfDirs) {
            fromFile = new File(FROM_PATH + dirName + "/" + XML_FILE);

            for (String toFileName : ToListOfFiles) {
                if (toFileName.contains("_" + dirName.substring(7, 9))) {
                    toFile = new File(TO_PATH + toFileName);

                    try {
                        sc = new Scanner(fromFile);

                        while (sc.hasNextLine()) {
                            line = sc.nextLine();
                            if (line.contains("\">")) {
                                keys.put("0", line.substring(line.indexOf("\">") + 2, line.indexOf("</")));
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        assert sc != null;
                        sc.close();
                    }
                    try {
                        sc = new Scanner(toFile);
                        result = MERGED_PATH + toFileName;

                        while (sc.hasNextLine()) {
                            line = sc.nextLine();

                            if (line.contains("</body>")) {
                                line = line
                                        .replace("</body>", "<p>" + keys.get("0") + "<br>\n"
//                                                + Links.LINK_4_1
//                                                + Links.LINK_4_2
//                                                + Links.LINK_4_3
//                                                + Links.LINK_4_4
                                                + "</p>\n</body>");
                            }
                            Writer.writeToFile(result, line);
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
    }
}

