package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.Paths.BASE_PATH;

public class ListOfDirs {

    private List<String> listOfDirs = new ArrayList<>();

    public List<String> returnList(String workingDir) {
        File translatedDir = new File(BASE_PATH + workingDir);

        for (File dir : Objects.requireNonNull(translatedDir.listFiles())) {
            if (dir.isDirectory()) {
                listOfDirs.add(dir.getName());
            }
        }
        return listOfDirs;
    }
}
