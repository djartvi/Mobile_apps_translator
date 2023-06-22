package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.Paths.BASE_PATH;

public class ListOfDirsToHtml {

    private List<String> listOfDirs = new ArrayList<>();

    public List<String> returnList() {
        File translatedDir = new File(BASE_PATH + "articles/");

        for (File dir : Objects.requireNonNull(translatedDir.listFiles())) {
            if (dir.isDirectory()) {
                listOfDirs.add(dir.getName());
                }
            }
        return listOfDirs;
    }
}
