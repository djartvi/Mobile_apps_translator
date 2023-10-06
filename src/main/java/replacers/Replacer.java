package replacers;

import files.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static files.Paths.*;

public class Replacer {
    Scanner sc;
    File file;
    String line, dir;

    public void replace(List<String> listOfDirs, String fromFileName, String toFileName) {
        new File(RESULT_PATH).mkdir();

        for (String dirName : listOfDirs) {
            file = new File(FROM_PATH + dirName + "/" + fromFileName);

            try {
                sc = new Scanner(file);
                dir = RESULT_PATH + dirName + "/" + toFileName;
                new File(dir).getParentFile().mkdirs();

                while (sc.hasNextLine()) {
                    line = sc.nextLine().replace("[]", "%@" );

                    if (line.contains("%@")) {
                        System.out.println(line);
                    }

                    Writer.writeToFile(dir, line);
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
