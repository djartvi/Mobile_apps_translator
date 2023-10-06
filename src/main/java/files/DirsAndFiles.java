package files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirsAndFiles {

    List<String> listOfDirs = new ArrayList<>();
    List<String> listOfFiles = new ArrayList<>();

    public List<String> createListOfDirs(String workingDir) {
        File translatedDir = new File(workingDir);

        for (File dir : Objects.requireNonNull(translatedDir.listFiles())) {
            if (dir.isDirectory()) {
                listOfDirs.add(dir.getName());
            }
        }
        return listOfDirs;
    }

    public List<String> createListOfFiles(String workingDir) {
        File translatedDir = new File(workingDir);

        for (File file : Objects.requireNonNull(translatedDir.listFiles())) {
            if (file.isFile() && !file.getName().startsWith(".")) {
                listOfFiles.add(file.getName());
            }
        }
        return listOfFiles;
    }

    public void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        dir.delete();
    }

    public void createDir(String path) {
        File dir = new File(path);
        deleteDir(dir);
        dir.mkdirs();
    }

    public void rewriteFile(String fullPath) {
        File file = new File(fullPath);
        if (file.isFile()) {
            file.delete();
        }

        file.getParentFile().mkdirs();
    }
}
