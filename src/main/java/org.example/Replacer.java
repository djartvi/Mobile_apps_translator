package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.example.Paths.BASE_PATH;
import static org.example.Paths.RESULT_PATH;

public class Replacer {

    private Scanner sc = null;

    File file;

    public void replace(List<String> listOfDirs) {
        Boolean newValuesDir = new File(RESULT_PATH).mkdir();

        for (String dirName : listOfDirs) {

            String strings = BASE_PATH + "values/%s";
            file = new File(String.format(strings, dirName));

            try {
                sc = new Scanner(file);

                String dir = String.format(RESULT_PATH + "%s", dirName.replace("html", "xml"));

                Boolean newDir = new File(dir).mkdir();

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String replaced;
                    if (!line.contains("<meta") && !line.contains("<head") && !line.contains("</head") && !line.contains("<html") && !line.contains("</html")) {
                        replaced = line
                                .replace("<body>", "<resources>")
                                .replace("</body>", "</resources>")
                                .replace("<strong>", "[[ ")
                                .replace("</strong>", " ]]")
                                .replace("<p>", "<string name=\"some\">")
                                .replace("</p>", "</string>");

                        try (FileWriter writer = new FileWriter(dir, true)) {
                            writer.write(replaced);
                            writer.append('\n');
                            writer.flush();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
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
