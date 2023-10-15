package files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirsAndFiles {

    private final List<String> listOfFiles = new ArrayList<>();


    public List<String> createListOfFiles(String workingDir) {
        recursion(workingDir);
        return listOfFiles;
    }

    // Проходимся по вложенным файлам. Если это не файл, а папка, то рекурсивно вызываем метод, пока не добавим все файлы
    public void recursion(String workingDir) {
        File translatedDir = new File(workingDir);

        for (File file : Objects.requireNonNull(translatedDir.listFiles())) {
            if (file.isDirectory()) {
                recursion(file.getAbsolutePath());
            } else if (file.isFile() && !file.getName().startsWith(".")) {
                listOfFiles.add(file.getAbsolutePath());
            }
        }
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
