package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.example.Paths.BASE_PATH;
import static org.example.Paths.RESULT_PATH;

public class ReplacerToHtml {

    private String strings = BASE_PATH + "articles/%s";
    private Scanner sc = null;
    private String line;
    private String replaced;

    File files;

    public void replaceBrackets(List<String> listOfDirs) {
        for (String dirName : listOfDirs) {

            files = new File(String.format(strings, dirName));

            for (File file : Objects.requireNonNull(files.listFiles())) {
                if (file.isFile() && !file.getName().startsWith(".")) {
                    try {
                        sc = new Scanner(file);

                        String resultFile = RESULT_PATH + dirName + "/" + file.getName().replace("xml", "html");
                        
                        new File(resultFile).getParentFile().mkdirs();

                        while (sc.hasNextLine()) {
                            line = sc.nextLine();
                            if (!line.contains("<?xml")) {
                                replaced = line
                                        .replace("<resources>", "<html>\n" +
                                                "  <head>\n" +
                                                "    <meta charset=\"utf-8\">\n" +
                                                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                                                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                                                "    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
                                                "    <meta http-equiv=\"x-rim-auto-match\" content=\"none\">\n" +
                                                "       <meta name=\"mobile-web-app-capable\" content=\"yes\">\n" +
                                                "    <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                                                "\n" +
                                                "</head>\n" +
                                                "\n" +
                                                "<body >")
                                        .replace("</body>", "</resources>")
                                        .replace("<string name=\"some\">", "<p>")
                                        .replace("[[ ", "<strong>")
                                        .replace(" ]]", "</strong>")
                                        .replace("[[", "<strong>")
                                        .replace("]]", "</strong>")
                                        .replace("</string>", "</p>")
                                        .replace("</resources>", "</body>\n" +
                                                "</html>");

                                try (FileWriter writer = new FileWriter(resultFile, true)) {
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
    }
}
