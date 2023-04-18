package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.Paths.CURRENT_PATH;

public class ListOfDirs {

    private List<String> listOfDirs = new ArrayList<>();

    public List<String> returnList() {
        File translatedDir = new File(CURRENT_PATH + "values/");

        for (File file : Objects.requireNonNull(translatedDir.listFiles())) {
            if (file.isFile()) {
                listOfDirs.add(file.getName());
            }
        }

        return listOfDirs;
    }
}
